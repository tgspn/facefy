import face_recognition
import cv2
import sys
import threading
import multiprocessing
import time
import os
import uuid
import random
import api

#parametros:
webcam = 1 # qual webcam usar( 0 = default, 1= webcam usb etc...)
velocidade_proc = 2 # quanto maior mais rapido o processamento, mas tbm pode pegar menos faces
path_queue = "imgs/queue/{}.png"
event_id = "7ffeb450-c571-4bfe-95cc-362b744d8e06"
base = api.load_img_from_Database()

def match_faces(img): 
    face_locations = face_recognition.face_locations(img)
    face_encodings = face_recognition.face_encodings(img, face_locations)
    for face_encoding in face_encodings:
        for img_encoing_database in base:
            try:
                matche = face_recognition.compare_faces([img_encoing_database['b64']], face_encoding)
                if(matche[0]):
                    print("achou o ",img_encoing_database['nome'])
                    print('id ',img_encoing_database['id'])
                    return img_encoing_database['id']
            except:
                return None
                print("nao eh foto ")
    return None

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
        id_ = match_faces(small_frame)
        if(id_ is not None):
            headers= {'Customer-Id':id_,'Content-Type':'application/json'}
            api.send_confirm_buy(event_id,headers,str(random.randrange(60000,99999)))
            break
        cv2.imshow('Video', frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    # When everything is done, release the capture
    video_capture.release()
    cv2.destroyAllWindows()

webcam_capture()