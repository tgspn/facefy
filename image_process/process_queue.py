import face_recognition
import cv2
import sys
import threading
import multiprocessing
import time
import os
from multiprocessing import Pool


def worker(img_full_path): 
    global imgs_database
    print(img_full_path)
    img = cv2.imread(img_full_path,1)
    small_img = cv2.resize(img, (0, 0), fx=float(1/velocidade_proc), fy=float(1/velocidade_proc))
    face_locations = face_recognition.face_locations(small_img)
    face_encodings = face_recognition.face_encodings(small_img, face_locations)
    for face_encoding in face_encodings:
        for img_database in imgs_database:
            matche = face_recognition.compare_faces([img_database], face_encoding)
            print(matche)
    os.remove(img_full_path)



    # face_encodings = face_recognition.face_encodings(rgb_small_frame, face_locations)
    # for face_encoding in face_encodings:
    #     matche = face_recognition.compare_faces([dhiogo_face_encoding], face_encoding)
    #     print(matche)


velocidade_proc = 2
imgs_database = list()
for i in range(0,100):
    dhiogo_image = face_recognition.load_image_file("dhiogo.jpg")
    small_dhiogo = cv2.resize(dhiogo_image, (0, 0), fx=float(1/4), fy=float(1/4))
    dhiogo_face_encoding = face_recognition.face_encodings(small_dhiogo)[0]
    imgs_database.append(dhiogo_face_encoding)
print("acabo")

path= "imgs/queue/"
while(True):
    if(len(os.listdir(path))==0):
        time.sleep(3)
        continue
    onlyfiles = [os.path.join(path, f) for f in os.listdir(path) ]
    time.sleep(3)
    p = Pool()
    p.map(worker, onlyfiles)
    p.close()
    p.join()
    
    # try:
    #     for img_path in onlyfiles:
    #         img_full_path = os.path.join(path, img_path)
    #         print(img_path)
    #         img = cv2.imread(img_full_path,1)
    #         small_img = cv2.resize(img, (0, 0), fx=float(1/velocidade_proc), fy=float(1/velocidade_proc))
    #         face_locations = face_recognition.face_locations(small_img)
    #         for img_database in imgs_database:
    #             worker(face_locations,img,img_database)
    #         os.remove(img_full_path)
    # except:
    #     print("preparando...")