import face_recognition
import cv2
import sys


video_capture = cv2.VideoCapture(1)
face_cascade = cv2.CascadeClassifier("casc.xml")
dhiogo_image = face_recognition.load_image_file("dhiogo.jpg")
dhiogo_face_encoding = face_recognition.face_encodings(dhiogo_image)[0]
while True:
    # Capture frame-by-frame
    ret, frame = video_capture.read()
    small_frame = frame
    #small_frame = cv2.resize(frame, (0, 0), fx=0.5, fy=0.5)
    rgb_small_frame = small_frame[:, :, ::-1]
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    face_locations = face_recognition.face_locations(rgb_small_frame)
    face_encodings = face_recognition.face_encodings(rgb_small_frame, face_locations)
    for face_encoding in face_encodings:
    # See if the face is a match for the known face(s)
        print(dhiogo_face_encoding.shape,face_encoding.shape)
        matches = face_recognition.compare_faces([dhiogo_face_encoding], face_encoding)
        print(matches)
    # face_locations = face_recognition.face_locations(image)
    for (top, right, bottom, left) in face_locations:
        # top *= 2
        # right *= 2
        # bottom *= 2
        # left *= 2
        # Draw a box around the face
        cv2.rectangle(frame, (left, top), (right, bottom), (0, 0, 255), 2)
    cv2.imshow('Video', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# When everything is done, release the capture
video_capture.release()
cv2.destroyAllWindows()