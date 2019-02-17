import face_recognition
import cv2
import sys
import threading
import multiprocessing
import time
import os
import uuid
#parametros:
webcam = 1 # qual webcam usar( 0 = default, 1= webcam usb etc...)
velocidade_proc = 2 # quanto maior mais rapido o processamento, mas tbm pode pegar menos faces
path_queue = "imgs/queue/{}.png"


def thread_write_img(path,frame):    
    cv2.imwrite(path,frame)

def webcam_capture():
    video_capture = cv2.VideoCapture(0)
    dhiogo_image = face_recognition.load_image_file("dhiogo.jpg")
    dhiogo_face_encoding = face_recognition.face_encodings(dhiogo_image)[0]
    catch_each_10_frames=1
    while True:
        # Capture frame-by-frame
        ret, frame = video_capture.read()
        small_frame = cv2.resize(frame, (0, 0), fx=float(1/velocidade_proc), fy=float(1/velocidade_proc))
        rgb_small_frame = small_frame[:, :, ::-1]
        face_locations = face_recognition.face_locations(rgb_small_frame)

        for (top, right, bottom, left) in face_locations:
            top *=velocidade_proc
            right *=velocidade_proc
            bottom *=velocidade_proc
            left *=velocidade_proc
            if(catch_each_10_frames==0):
                multiprocessing.Process(target=thread_write_img,args=(path_queue.format(str(uuid.uuid4())),frame,)).start()
            cv2.rectangle(frame, (left, top), (right, bottom), (0, 0, 255), 2)
            catch_each_10_frames = catch_each_10_frames +1
            if(catch_each_10_frames>5):
                catch_each_10_frames=0
        cv2.imshow('Video', frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    # When everything is done, release the capture
    video_capture.release()
    cv2.destroyAllWindows()

webcam_capture()