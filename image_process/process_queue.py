import face_recognition
import cv2
import sys
import threading
import multiprocessing
import time
import os



def worker(face_locations,rgb_small_frame,dhiogo_face_encoding):    
    face_encodings = face_recognition.face_encodings(rgb_small_frame, face_locations)
    for face_encoding in face_encodings:
        matche = face_recognition.compare_faces([dhiogo_face_encoding], face_encoding)
        print(matche)


dhiogo_image = face_recognition.load_image_file("dhiogo.jpg")
dhiogo_face_encoding = face_recognition.face_encodings(dhiogo_image)[0]
path= "imgs/queue/"
while(True):
    if(len(os.listdir(path))==0):
        continue
    onlyfiles = [f for f in os.listdir(path) ]
    time.sleep(3)
    try:
        for img_path in onlyfiles:
            img_full_path = os.path.join(path, img_path)
            print(img_path)
            img = cv2.imread(img_full_path,1)
            face_locations = face_recognition.face_locations(img)
            worker(face_locations,img,dhiogo_face_encoding)
            os.remove(img_full_path)
    except:
        print("preparando...")