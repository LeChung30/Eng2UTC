import uuid
from datetime import datetime

import firebase_admin
import pandas as pd
import pyrebase
from firebase_admin import credentials
from firebase_admin import db

import english as eng
import EngSecurity as sec

# upload file to firebase
firebaseConfig = {
    'apiKey': "AIzaSyAwW6pc0aQGXSA-ndqBPM3Gp8e5jqCaIB0",
    'authDomain': "eng2utc.firebaseapp.com",
    'databaseURL': "https://eng2utc-default-rtdb.firebaseio.com",
    'projectId': "eng2utc",
    'storageBucket': "eng2utc.appspot.com",
    'messagingSenderId': "679823761990",
    'appId': "1:679823761990:web:00cfe2a91e5d43440e6047",
    'measurementId': "G-X9047FEREQ"
}
local_path_mp3 = 'data/mp3/pronunciation/'
remote_path_mp3 = 'mp3/pronunciation/'
firebase = pyrebase.initialize_app(firebaseConfig)
auth = firebase.auth()

# Khởi tạo Firebase Admin SDK

def connect_firebase():
    file_path = 'data/key/eng2utc-firebase-adminsdk-y4ymw-0ab02a4678.json'
    try:
        cred = credentials.Certificate(file_path)  # Đường dẫn đến tệp JSON xác thực của bạn
        firebase_admin.initialize_app(cred, {
            'databaseURL': 'https://eng2utc-default-rtdb.firebaseio.com/'  # Thay bằng tên cơ sở dữ liệu của bạn
        })
        # Kiểm tra kết nối
        if firebase_admin.get_app():
            print("Connect to Firebase successfully")
    except Exception as e:
        print(f"Connect to Firebase failed: {e}")

# Gọi hàm để kiểm tra kết nối
connect_firebase()



def authenticate_user(email, password):
    try:
        user = auth.sign_in_with_email_and_password(email, password)
        print(f"User {email} authenticated successfully")
        return user
    except Exception as e:
        print(f"Error authenticating user {email}: {e}")
        return None

def add_user(email, user_name, full_name, gender, is_active, password):
    user_id = str(uuid.uuid4())
    hash_password = sec.hash_bcrypt(password)
    user_data = {
        'USER_ID': user_id,
        'EMAIL': email,
        'USER_NAME': user_name,
        'FULL_NAME': full_name,
        'GENDER': gender,
        'IS_ACTIVE': is_active,
        'CREATED_DATE': datetime.now().isoformat(),  # Current time in ISO format
        'HASH_PASSWORD': hash_password,  # Ensure password is hashed before storing
    }

    # Add user to Realtime Database
    db.reference('USER').child(user_id).set(user_data)  # Use set() with user_id as the key
    print(f"User added successfully with ID: {user_id}")


# push

def upload_file_mp3(word: str, id_token):
    # Sanitize the word to create a valid file name
    sanitized_word = eng.sanitize_filename(word)
    local_file = local_path_mp3 + sanitized_word + '.mp3'
    remote_file = remote_path_mp3 + sanitized_word + '.mp3'

    storage = firebase.storage()
    storage.child(remote_file).put(local_file, id_token)
    print(f"File uploaded successfully to {remote_file}")
    return storage.child(remote_file).get_url(id_token)

def add_vocabbulary(word, pronunciation, part_of_speech, image_link, meaning, cert_level_name,
                    topic_name, id_token):
    # plartern cert level,topic id
    vocab_id = str(uuid.uuid4())
    audio_link = upload_file_mp3(word, id_token)
    vocab_data = {
        'VOCAB_ID': vocab_id,
        'WORD': word,
        'PRONUNCIATION': pronunciation,
        'PART_OF_SPEECH': part_of_speech,
        'IMAGE_LINK': image_link,
        'AUDIO_LINK': audio_link,
        'MEANING': meaning,
        'CERT_LEVEL_NAME': cert_level_name,
        'TOPIC_NAME': topic_name,
    }
    db.reference('VOCABULARY').child(vocab_id).set(vocab_data)
    print(f"Vocabulary added successfully with ID: {vocab_id}")

def add_cert_level(level_name, description, image_link):
    cert_level_id = str(uuid.uuid4())
    cert_level_data = {
        'CERT_LEVEL_ID': cert_level_id,
        'CERT_LEVEL_NAME': level_name,
        'DESCRIPTION': description,
        'IMAGE_LINK': image_link,
    }
    db.reference('CERT_LEVEL').child(cert_level_id).set(cert_level_data)
    print(f"Cert Level added successfully with ID: {cert_level_id}")

def add_topic(topic_name, description, image_link):
    topic_id = str(uuid.uuid4())
    topic_data = {
        'TOPIC_ID': topic_id,
        'TOPIC_NAME': topic_name,
        'DESCRIPTION': description,
        'IMAGE_LINK': image_link,
    }
    db.reference('TOPIC').child(topic_id).set(topic_data)
    print(f"Topic added successfully with ID: {topic_id}")


def add_lession(name_of_lesson, image_link, cert_name, is_vocab, order, topic_name):
    # plattern certlevel, topic id

    lession_id = str(uuid.uuid4())
    cert_id = get_id_by_cert_level_name(cert_name)
    topic_id = get_id_by_topicname(topic_name)
    vocabs = get_vocab_by_topicname_and_certname(topic_id, cert_id)
    lession = {
        'LESSON_ID': lession_id,
        'NAME_OF_LESSON': name_of_lesson,
        'IMAGE_LINK': image_link,
        'CERT_LEVEL_NAME': cert_name,
        'IS_VOCAB': is_vocab,
        'ORDER': order,
        'TOPIC_NAME': topic_name,
        'VOCABULARY': vocabs
    }
    db.reference('LESSON').child(lession_id).set(lession)
    print(f"Lesson added successfully with ID: {lession_id}")

def add_question(content, audio_link, image_link, correct_anwer_id, question_type_id, part_detail_id,
                 question_content_id):  # add answer if selected answers then plattern
    # plattern question type
    question_id = str(uuid.uuid4())
    question = {
        'QUESTION_ID': question_id,
        'CONTENT': content,
        'AUDIO_LINK': audio_link,
        'IMAGE_LINK': image_link,
        'CORRECT_ANSWER_ID': correct_anwer_id,
        'QUESTION_TYPE_ID': question_type_id,
        'PART_DETAI_ID': part_detail_id,
        'QUESTION_CONTENT_ID': question_id,
    }
    db.reference('QUESTION').child(question_id).set(question)
    print(f"Question added successfully with ID: {question_id}")


def add_answer(content, image_link, audio_link, question_id):
    answer_id = str(uuid.uuid4())
    answer = {
        'ANSWER_ID': answer_id,
        'CONTENT': content,
        'IMAGE_LINK': image_link,
        'AUDIO_LINK': audio_link,
        'QUESTION_ID': question_id,
    }
    db.reference('LESSON').child(answer_id).set(answer)
    print(f"Answer attending added successfully with ID: {answer_id}")

def add_question_type(name_of_type):
    question_type_id = str(uuid.uuid4())
    question_type = {
        'QUESTION_TYPE_ID': question_type_id,
        'NAME_OF_TYPE': name_of_type,
    }
    db.reference('QUESTION_TYPE').child(question_type_id).set(question_type)
    print(f"Question type added successfully with ID: {question_type_id}")


def add_test_type(name_of_test_type, total_duration, maximum, cert_level_name):  # plattern cert_test
    # PLATTERN CERT ID, CHANGE NAME
    test_type_id = str(uuid.uuid4())
    test_type = {
        'TEST_TYPE_ID': test_type_id,
        'NAME_OF_TYPE_TEST': name_of_test_type,
        'TOTAL_DURATION': total_duration,
        'MAXIMUM_SCORE': maximum,
        'CERT_LEVEL_NAME': cert_level_name,
    }
    db.reference('TEST_TYPE').child(test_type_id).set(test_type)
    print(f"Type test added successfully with ID: {test_type_id}")


def add_part_of_test(name_of_part, test_type_id):
    part_of_test_id = str(uuid.uuid4())
    name_of_test_type = get_id_test_type_by_name(test_type_id)
    part_of_test = {
        'PART_OF_TEST_ID': part_of_test_id,
        'NAME_OF_PART': name_of_part,
        'TEST_TYPE_NAME': name_of_test_type,
    }
    db.reference('PART_OF_TEST').child(part_of_test_id).set(part_of_test)
    print(f"Part of test added successfully with ID: {part_of_test_id}")

def add_question_content(content, image_link, audio_link):
    question_content_id = str(uuid.uuid4())
    answer = {
        'QUESTION_CONTENT_ID': question_content_id,
        'CONTENT': content,
        'IMAGE_LINK': image_link,
        'AUDIO_LINK': audio_link
    }
    db.reference('QUESTION_CONTENT').child(question_content_id).set(answer)
    print(f"Date attending added successfully with ID: {question_content_id}")

def add_test(name_of_test, test_type_id):
    # add information
    test_id = str(uuid.uuid4())
    test = {
        'NAME_OF_TEST': name_of_test,
        'TEST_TYPE_ID': test_type_id
    }
    db.reference('TEST').child(test_id).set(test)
    print(f"Test added successfully with ID: {test_id}")

def add_part_detail(name_of_part_detail, test_id, audio_link, image_link):
    part_detail_id = str(uuid.uuid4())
    part_detail = {
        'PART_DETAIL_ID': part_detail_id,
        'NAME_OF_PART_DETAIL': name_of_part_detail,
        'TEST_ID': test_id,
        'AUDIO_LINK': audio_link,
        'IMAGE_LINK': image_link
    }
    db.reference('PART_DETAIL').child(part_detail_id).set(part_detail)
    print(f"Part detail added successfully with ID: {part_detail_id}")

# get
def get_alltopic():
    try:
        r = db.reference('TOPIC').get()
    except Exception as e:
        print(f"Error get all topic: {e}")
        r = None
    return r

def get_topic_by_name(topic_name):
    try:
        return db.reference('TOPIC').order_by_child('TOPIC_NAME').equal_to(topic_name).get()
    except Exception as e:
        print(e)
        return -1

def get_all_cert_level():
    return db.reference('CERT_LEVEL').get()

def get_cert_level_by_name(level_name):
    try:
        return db.reference('CERT_LEVEL').order_by_child('CERT_LEVEL_NAME').equal_to(level_name).get()
    except Exception as e:
        print(e)
        return -1

def get_vocabulary_by_question_id(question_id):
    return db.reference('QUESTION').order_by_child('QUESTION_ID').equal_to(question_id).get()

def get_vocabulary_by_id(vocab_id):
    return db.reference('VOCABULARY').order_by_child('VOCAB_ID').equal_to(vocab_id).get()


def get_id_test_type_by_name(name_of_test_type):
    try:
        res = db.reference('TEST_TYPE').order_by_child('NAME_OF_TEST_TYPE').equal_to(name_of_test_type).get()
        return res
    except Exception as e:
        print(e)
        return -1

def get_vocabulary_by_topic_name(topic_name):
    try:
        # Step 1: Find the topic ID by topic name
        topics = db.reference('TOPIC').order_by_child('TOPIC_NAME').equal_to(topic_name).get()
        if not topics:
            print(f"No topic found with the name: {topic_name}")
            return None

        # Assuming topic_name is unique and we get a single result
        topic_id = list(topics.keys())[0]

        # Step 2: Retrieve vocabulary data for the found topic ID
        vocabulary_data = db.reference(f'TOPIC/{topic_id}/VOCABULARY').get()
        if vocabulary_data:
            return vocabulary_data
        else:
            print(f"No vocabulary data found for topic: {topic_name}")
            return None
    except Exception as e:
        print(e)
        return None


def get_id_by_cert_level_name(cert_level_name):
    try:
        cert_levels = db.reference('CERT_LEVEL').order_by_child('CERT_LEVEL_NAME').equal_to(cert_level_name).get()
        if not cert_levels:
            print(f"No cert level found with the name: {cert_level_name}")
            return None

        # Assuming cert_level_name is unique and we get a single result
        cert_level_id = list(cert_levels.keys())[0]
        return cert_level_id
    except Exception as e:
        print(e)
        return None


def get_vocabulary_by_cert_level_name(cert_level_name):
    try:
        # Retrieve all vocabulary data
        vocab_data = db.reference('TOPIC').get()
        if not vocab_data:
            print("No vocabulary data found.")
            return None

        # Filter words by certification level
        words_with_level = []
        for vocab_id, vocab_info in vocab_data.items():
            cert_levels = vocab_info.get('CERT_LEVEL_ID', {})
            for cert_id, cert_info in cert_levels.items():
                if cert_info.get('LEVEL_NAME') == cert_level_name:
                    words_with_level.append({
                        'WORD': vocab_info.get('WORD'),
                        'MEANING': vocab_info.get('MEANING'),
                        'PRONUNCIATION': vocab_info.get('PRONUNCIATION'),
                        'AUDIO_LINK': vocab_info.get('AUDIO_LINK'),
                        'LEVEL_NAME': cert_info.get('LEVEL_NAME')
                    })

        return words_with_level
    except Exception as e:
        print(e)
        return None


def get_vocab_by_topicname_and_certname(topic_id, cert_id):
    try:
        # Step 1: Query by TOPIC_ID
        vocab_data = db.reference('VOCABULARY').order_by_child('TOPIC_NAME').equal_to(topic_id).get()

        # Step 2: Filter by CERT_LEVEL_ID
        filtered_vocab = {}
        for vocab_id, vocab_info in vocab_data.items():
            cert_levels = vocab_info.get('CERT_LEVEL_NAME', {})
            if cert_id in cert_levels:
                filtered_vocab[vocab_id] = vocab_info

        return filtered_vocab
    except Exception as e:
        print(e)
        return None


def get_id_by_topicname(topic_name):
    try:
        topics = db.reference('TOPIC').order_by_child('TOPIC_NAME').equal_to(topic_name).get()
        if not topics:
            print(f"No topic found with the name: {topic_name}")
            return None

        # Assuming topic_name is unique and we get a single result
        topic_id = list(topics.keys())[0]
        return topic_id
    except Exception as e:
        print(e)
        return None


# push arrange

def add_rang_vocab(file):
    #phai them vocab truoc
    connect_firebase()
    gmail, password = 'eng2UTC@gmail.com', 'eng2UTC@123'
    user = authenticate_user(gmail, password)
    vocab = eng.dataframe_nan_to_none(eng.read_csv(file))
    for index, row in vocab.iterrows():
        eng.to_mp3(row['WORD'])
        add_vocabbulary(row['WORD'], row['PRONUNCIATION'], row['PART_OF_SPEECH'], row['IMAGE_LINK'], row['MEANING'],
                        row['CERT_LEVEL_NAME'], row['TOPIC_NAME'], user['idToken'])

    print("Add vocabulary successfully")


def add_range_topic(file):
    top = eng.read_csv(file)
    for index, row in top.iterrows():
        add_topic(topic_name=row['TOPIC_NAME'], description=row['DESCRIPTION'], image_link=row['DESCRIPTION'])


def add_range_lesson(file):
    data = eng.read_csv(file)
    for index, row in data.iterrows():
        add_lession(name_of_lesson=row['NAME_OF_LESSON'], image_link=row['IMAGE_LINK'],
                    cert_name=row['CERT_LEVEL_NAME'],
                    is_vocab=row['IS_VOCAB'], order=row['ORDER'], topic_name=row['TOPIC_NAME'])
    print("Add lesson successfully")


def add_range_test_type(file):
    data = eng.read_csv(file)
    for index, row in data.iterrows():
        add_test_type(name_of_test_type=row['NAME_OF_TEST_TYPE'], total_duration=row['TOTAL_DURATION'],
                      maximum=row['MAXIMUM_SCORE'], cert_level_name=row['CERT_LEVEL_NAME'])
    print("Add test type successfully")


def add_range_part_of_test(file):
    data = eng.read_csv(file)
    for index, row in data.iterrows():
        add_part_of_test(name_of_part=row['NAME_OF_PART'], test_type_id=row['TEST_TYPE_NAME'])
    print("Add part of test successfully")


def add_range_cert_level(file):
    data = eng.read_csv(file)
    for index, row in data.iterrows():
        add_cert_level(level_name=row['CERT_LEVEL_NAME'], description=row['DESCRIPTION'], image_link=row['IMAGE_LINK'])
    print("Add cert level successfully")

def add_range_question_type(file):
    data=eng.read_csv(file)
    for index,row in data.iterrows():
        add_question_type(name_of_type=row['NAME_OF_TYPE'])
    print('Add question type successfully')

def add_range_user(file):
    data=eng.read_csv(file)
    data['DATE_OF_BIRTH'] = pd.to_datetime(data['DATE_OF_BIRTH'], format='%b,%d,%Y')
    data['CREATED_DATE'] = pd.to_datetime(data['CREATED_DATE'], format='%b,%d,%Y')
    data['PHONE_NUMBER'] = data['PHONE_NUMBER'].apply(lambda x: str(x))
    data['PHONE_NUMBER'] = data['PHONE_NUMBER'].str.replace('-', '', regex=False)
    for index,row in data.iterrows():
        add_user(email=row['EMAIL'],user_name=row['USER_NAME'],full_name=row['FULL_NAME'],gender=row['GENDER'],is_active=row['IS_ACTIVE'],password=row['PASSWORD'])
    print('Add user successfully')

if __name__ == '__main__':
    # add_range_cert_level('data/csv/cert_level.csv')
    # add_range_topic('data/csv/topic.csv')
    # add_rang_vocab('data/csv/vocabulary_A1.csv')
    # add_rang_vocab('data/csv/vocabulary_A2.csv')
    # add_range_lesson('data/csv/lesson.csv')
    # add_range_test_type('data/csv/test_type.csv')
    # add_range_part_of_test('data/csv/part_of_test.csv')
    # add_range_user('data/csv/user.csv')
    pass


