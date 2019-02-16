import cv2
import sys

video_capture = cv2.VideoCapture(1)
face_cascade = cv2.CascadeClassifier("casc.xml")
while True:
    # Capture frame-by-frame
    ret, frame = video_capture.read()

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    crop_img = list()
    for (x,y,w,h) in faces:
        crop_img.append(frame[y:y+h, x:x+w])
        cv2.rectangle(frame,(x,y),(x+w,y+h),(255,0,0),2)
    cv2.imshow('Video', frame)
    for crop in crop_img:
        cv2.imshow('', crop)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# When everything is done, release the capture
video_capture.release()
cv2.destroyAllWindows()