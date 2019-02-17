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
path_fotos = ""
process_this_frame = True

def worker(path,frame):    
    cv2.imwrite(path,frame)



video_capture = cv2.VideoCapture(0)
dhiogo_image = face_recognition.load_image_file("dhiogo.jpg")
dhiogo_face_encoding = face_recognition.face_encodings(dhiogo_image)[0]
i=1
while True:
    # Capture frame-by-frame
    ret, frame = video_capture.read()
    small_frame = cv2.resize(frame, (0, 0), fx=float(1/velocidade_proc), fy=float(1/velocidade_proc))
    rgb_small_frame = small_frame[:, :, ::-1]
    face_locations = face_recognition.face_locations(rgb_small_frame)
    #multiprocessing.Process(target=worker,args=(face_locations, rgb_small_frame,dhiogo_face_encoding,)).start()

    for (top, right, bottom, left) in face_locations:
        top *=velocidade_proc
        right *=velocidade_proc
        bottom *=velocidade_proc
        left *=velocidade_proc
        #crop_img = frame[top-10:bottom+10, left-10:right+10]

        print(i)
        if(i==0):
            multiprocessing.Process(target=worker,args=("imgs/queue/{}.png".format(str(uuid.uuid4())),frame,)).start()
        cv2.rectangle(frame, (left, top), (right, bottom), (0, 0, 255), 2)
        i = i +1
        
        if(i>5):
            i=0
    cv2.imshow('Video', frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# When everything is done, release the capture
video_capture.release()
cv2.destroyAllWindows()
