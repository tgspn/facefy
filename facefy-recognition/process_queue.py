import face_recognition
import cv2
import sys
import threading
import multiprocessing
import time
import os
from multiprocessing import Pool
import b64_img
import api
import json

#parametros
velocidade_proc = 2
path= "imgs/queue/"
event_id = "7ffeb450-c571-4bfe-95cc-362b744d8e06"

#variavel global
custumers_encoing_img_database = list()

def match_faces(img_full_path): 
    global custumers_encoing_img_database
    img = cv2.imread(img_full_path,1)
    #small_img = cv2.resize(img, (0, 0), fx=float(1/velocidade_proc), fy=float(1/velocidade_proc))
    face_locations = face_recognition.face_locations(img)
    face_encodings = face_recognition.face_encodings(img, face_locations)
    for face_encoding in face_encodings:
        for img_encoing_database in custumers_encoing_img_database:
            try:
                matche = face_recognition.compare_faces([img_encoing_database['b64']], face_encoding,tolerance=0.5)
                # print(matche[0])
                if(matche[0]):
                    print("achou o ",img_encoing_database['nome'])
                    break
            except:
                print("nao eh foto ")
    os.remove(img_full_path)



def infinit_process():
    print("start infinit process...")
    while(True):
        if(len(os.listdir(path))==0):
            time.sleep(3)
            continue
        onlyfiles = [os.path.join(path, f) for f in os.listdir(path) ]
        time.sleep(3)
        p = Pool()
        p.map(match_faces, onlyfiles)
        p.close()
        p.join()



custumers_encoing_img_database = api.load_img_from_Database()
infinit_process()