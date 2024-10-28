import uuid

import firebase_admin
import pandas as pd
import pyrebase
from firebase_admin import credentials
from firebase_admin import db

import EngSecurity as sec
import ProcessFile as pfl

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

local_path_pronunciation_mp3 = 'data/mp3/pronunciation/'
local_pat_question_mp3 = 'data/mp3/question/'
remote_path_pronunciation_mp3 = 'mp3/pronunciation/'
remote_path_question_mp3 = 'mp3/question/'
local_path_img = 'data/img/'
remote_path_img = 'img/'
firebase = pyrebase.initialize_app(firebaseConfig)
auth = firebase.auth()
file_path = 'D:\Code\python\project_1\Eng2UTC\Backend\data\key\eng2utc-firebase-adminsdk-y4ymw-0ab02a4678.json'
data_base_url='https://eng2utc-default-rtdb.firebaseio.com/'
# Khởi tạo Firebase Admin SDK

def connect_firebase():

    try:
        cred = credentials.Certificate(file_path)  # Đường dẫn đến tệp JSON xác thực của bạn
        firebase_admin.initialize_app(cred, {
            'databaseURL': data_base_url  # Thay bằng tên cơ sở dữ liệu của bạn
        })
        # Kiểm tra kết nối
        if firebase_admin.get_app():
            print("Connect to Firebase successfully")
    except Exception as e:
        print(f"Connect to Firebase failed: {e}")

# Gọi hàm để kiểm tra kết nối
connect_firebase()


def sign_up_user_authencation(email, password):
    try:
        user = auth.create_user_with_email_and_password(email, password)
        print(f"User {email} signed up successfully")
        return user
    except Exception as e:
        print(f"Error signing up user {email}: {e}")
        return None

def authenticate_user(email, password):
    try:
        user = auth.sign_in_with_email_and_password(email, password)
        print(f"User {email} authenticated successfully")
        return user
    except Exception as e:
        print(f"Error authenticating user {email}: {e}")
        return None

# push

def upload_file_img(file, r_file, id_token):
    try:
        local = local_path_img + str(int(file)) + '.png'
    except Exception as e:
        print(f"Error upload file: {e}")
        return None
    remote = remote_path_img + r_file + '.png'
    storage = firebase.storage()
    storage.child(remote).put(local, id_token)
    print(f"File uploaded successfully to {remote}")
    return storage.child(remote).get_url(id_token)


def upload_file_mp3(local_file, remote_file, id_token):
    # Sanitize the word to create a valid file name

    storage = firebase.storage()
    storage.child(remote_file).put(local_file, id_token)
    print(f"File uploaded successfully to {remote_file}")
    return storage.child(remote_file).get_url(id_token)


def add_user(full_name, age, gender, birth_of_date, email, phone_number, user_name, created_date, password, is_active):
    user_id = str(uuid.uuid4())
    hash_password = sec.hash_bcrypt(password)

    # Chuyển đổi Timestamp thành chuỗi
    if isinstance(birth_of_date, pd.Timestamp):
        birth_of_date = birth_of_date.strftime('%Y-%m-%d')
    if isinstance(created_date, pd.Timestamp):
        created_date = created_date.strftime('%Y-%m-%d')

    user_data = {
        'USER_ID': user_id,
        'FULL_NAME': full_name,
        'AGE': age,
        'GENDER': gender,
        'BIRTH_OF_DATE': birth_of_date,
        'EMAIL': email,
        'PHONE_NUMBER': phone_number,
        'USER_NAME': user_name,
        'CREATED_DATE': created_date,
        'PASSWORD': hash_password,
        'IS_ACTIVE': is_active
    }
    try:
        db.reference('USER').child(user_id).set(user_data)  # Use set() with user_id as the key
        print(f'Successfully added user: {full_name}')

    except Exception as e:
        print(f"Error adding user: {e}")
        return
    return user_data


def add_vocabbulary(word, pronunciation, part_of_speech, meaning, topic_name, image_link,
                    cert_level_name, id_token):
    # plartern cert level,topic id
    vocab_id = str(uuid.uuid4())
    sanitized_word = pfl.sanitize_filename(word)
    local_file = local_path_pronunciation_mp3 + sanitized_word + '.mp3'
    remote_file = remote_path_pronunciation_mp3 + sanitized_word + '.mp3'
    audio_link = upload_file_mp3(local_file, remote_file, id_token)
    vocab_data = {
        'VOCAB_ID': vocab_id,
        'WORD': word,
        'PRONUNCIATION': pronunciation,
        'PART_OF_SPEECH': part_of_speech,
        'MEANING': meaning,
        'TOPIC_NAME': topic_name,
        'CERT_LEVEL_ID': get_id_by_cert_level_name(cert_level_name),
        'TOPIC_ID': get_id_by_topicname(topic_name),
        'CERT_LEVEL_NAME': cert_level_name,
        'AUDIO_LINK': audio_link,
        'IMAGE_LINK': image_link
    }
    db.reference('VOCABULARY').child(vocab_id).set(vocab_data)
    print(f"Vocabulary added successfully with ID: {vocab_id}")
    return vocab_data

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
    return cert_level_data

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
    return topic_data


def add_lesson(name_of_lesson, image_link, is_vocab, order, topic_name, cert_level_name):
    lesson_id = str(uuid.uuid4())
    cert_id = get_id_by_cert_level_name(cert_level_name)
    topic_id = get_id_by_topicname(topic_name)
    vocabs = get_vocab_by_topicname_and_certname(topic_id, cert_id)
    lesson = {
        'LESSON_ID': lesson_id,
        'NAME_OF_LESSON': name_of_lesson,
        'IMAGE_LINK': image_link,
        'CERT_LEVEL_ID': cert_id,
        'IS_VOCAB': is_vocab,
        'ORDER': order,
        'TOPIC_ID': topic_id,
        'VOCABULARY': vocabs,
        'TOPIC_NAME': topic_name,
        'CERT_LEVEL_NAME': cert_level_name
    }

    db.reference('LESSON').child(lesson_id).set(lesson)
    print(f"Lesson added successfully with ID: {lesson_id}")
    return lesson


def add_question(content, audio_link, image_link, correct_anwer_id, question_type_id, part_detail_id,
                 question_content_id, order, test_id):  # add answer if selected answers then plattern
    # plattern question type
    question_id = str(uuid.uuid4())
    question_data = {
        'QUESTION_ID': question_id,
        'CONTENT': content,
        'AUDIO_LINK': audio_link,
        'IMAGE_LINK': image_link,
        'CORRECT_ANSWER_ID': correct_anwer_id,
        'QUESTION_TYPE_ID': question_type_id,
        'PART_DETAIL_ID': part_detail_id,
        'QUESTION_CONTENT_ID': question_content_id,
        'ORDER': order,
        'TEST_ID': test_id,
    }
    db.reference('QUESTION').child(question_id).set(question_data)
    print(f"Question added successfully with ID: {question_id}")
    return question_data


def add_answer(content, image, audio_link, question_id, id_token):
    answer_id = str(uuid.uuid4())
    if image is not None:
        image_link = upload_file_img(image, answer_id, id_token)
    else:
        image_link = None
    answer = {
        'ANSWER_ID': answer_id,
        'CONTENT': content,
        'IMAGE_LINK': image_link,
        'AUDIO_LINK': audio_link,
        'QUESTION_ID': question_id,
    }
    db.reference('ANSWER').child(answer_id).set(answer)
    print(f"Answer added successfully with ID: {answer_id}")
    return answer

def add_question_type(name_of_type):
    question_type_id = str(uuid.uuid4())
    question_type = {
        'QUESTION_TYPE_ID': question_type_id,
        'NAME_OF_TYPE': name_of_type,
    }
    db.reference('QUESTION_TYPE').child(question_type_id).set(question_type)
    print(f"Question type added successfully with ID: {question_type_id}")
    return question_type


def add_test_type(name_of_test_type, total_duration, maximum, cert_level_name):  # plattern cert_test
    # PLATTERN CERT ID, CHANGE NAME
    test_type_id = str(uuid.uuid4())
    test_type = {
        'TEST_TYPE_ID': test_type_id,
        'NAME_OF_TYPE_TEST': name_of_test_type,
        'TOTAL_DURATION': total_duration,
        'MAXIMUM_SCORE': maximum,
        'CERT_LEVEL_NAME': cert_level_name,
        'CERT_LEVEL_ID': get_id_by_cert_level_name(cert_level_name)
    }
    db.reference('TEST_TYPE').child(test_type_id).set(test_type)
    print(f"Type test added successfully with ID: {test_type_id}")
    return test_type


def add_part_of_test(name_of_part, test_type_name):
    part_of_test_id = str(uuid.uuid4())
    test_type_id = get_id_test_type_by_name(test_type_name)

    part_of_test = {
        'PART_OF_TEST_ID': part_of_test_id,
        'NAME_OF_PART': name_of_part,
        'TEST_TYPE_ID': test_type_id,
    }
    db.reference('PART_OF_TEST').child(part_of_test_id).set(part_of_test)
    print(f"Part of test added successfully with ID: {part_of_test_id}")
    return part_of_test


def add_question_content(content):
    question_content_id = str(uuid.uuid4())
    ques_content = {
        'QUESTION_CONTENT_ID': question_content_id,
        'CONTENT': content
    }
    db.reference('QUESTION_CONTENT').child(question_content_id).set(ques_content)
    print(f"Question content added successfully with ID: {question_content_id}")
    return ques_content

def add_test(name_of_test, test_type_id):
    # add information
    test_id = str(uuid.uuid4())
    test = {
        'TEST_ID': test_id,
        'NAME_OF_TEST': name_of_test,
        'TEST_TYPE_ID': test_type_id
    }
    db.reference('TEST').child(test_id).set(test)
    print(f"Test added successfully with ID: {test_id}")
    return test


def add_part_detail(part_of_test_id, test_id, audio_link, image_link, order):
    part_detail_id = str(uuid.uuid4())
    part_detail = {
        'PART_DETAIL_ID': part_detail_id,
        'PART_OF_TEST_ID': part_of_test_id,
        'TEST_ID': test_id,
        'AUDIO_LINK': audio_link,
        'IMAGE_LINK': image_link,
        'ORDER': order
    }
    db.reference('PART_DETAIL').child(part_detail_id).set(part_detail)
    print(f"Part detail added successfully with ID: {part_detail_id}")
    return part_detail

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
        # get id test type by name
        test_types = db.reference('TEST_TYPE').order_by_child('NAME_OF_TYPE_TEST').equal_to(name_of_test_type).get()
        if not test_types:
            print(f"No test type found with the name: {name_of_test_type}")
            return None
        # Assuming test type name is unique and we get a single result
        test_type_id = list(test_types.keys())[0]
        return test_type_id

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
            print(f"No certification level found with the name: {cert_level_name}")
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
        vocabs = db.reference('VOCABULARY').order_by_child('TOPIC_ID').equal_to(topic_id).get()
        if not vocabs:
            print(f"No vocabulary found for topic ID: {topic_id}")
            return None
        else:
            # Filter by CERT_LEVEL_ID return vocab list
            vocab_list = []
            for vocab_id, vocab_info in vocabs.items():
                if vocab_info.get('CERT_LEVEL_ID') == cert_id:
                    vocab_list.append(vocab_info)
            return vocab_list
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


def get_all_question_type():
    return db.reference('QUESTION_TYPE').get()


def get_question_by_test_id(test_id):
    try:
        return db.reference('QUESTION').order_by_child('TEST_ID').equal_to(test_id).get()
    except Exception as e:
        print(e)
        return -1
# push arrange
#khải: lấy question và part_details bằng test_idF
def get_data_by_test_id(test_id):
    try:
        # Lấy dữ liệu từ bảng part_detail
        part_details = db.reference('PART_DETAIL').order_by_child('TEST_ID').equal_to(test_id).get()
        
        # Lấy dữ liệu từ bảng question
        questions = db.reference('QUESTION').order_by_child('TEST_ID').equal_to(test_id).get()

        # Tạo dictionary để lưu trữ part_detail với các câu hỏi tương ứng
        part_detail_dict = {}
        for part_id, part_info in part_details.items():
            part_info['questions'] = []
            part_detail_dict[part_id] = part_info

        # Gán các câu hỏi vào part_detail tương ứng
        for question_id, question_info in questions.items():
            part_id = question_info.get('PART_DETAIL_ID')
            if part_id in part_detail_dict:
                part_detail_dict[part_id]['questions'].append(question_info)

        # Sắp xếp các câu hỏi theo cột Order
        for part_id, part_info in part_detail_dict.items():
            part_info['questions'] = sorted(part_info['questions'], key=lambda x: x.get('ORDER', 0))

        # Sắp xếp kết quả part_detail theo cột Order
        sorted_part_details = sorted(part_detail_dict.values(), key=lambda x: x.get('ORDER', 0))

        return {
            "part_details": sorted_part_details
        }
    except Exception as e:
        print(e)
        return -1
def add_range_vocab(file):
    # Phải thêm vocab trước
    gmail, password = 'eng2UTC@gmail.com', 'eng2UTC@123'
    user = authenticate_user(gmail, password)

    if user is None or 'idToken' not in user:
        print("Authentication failed. Please check your credentials.")
        return

    vocab = pfl.dataframe_nan_to_none(pfl.read_csv(file))
    vocab['VOCAB_ID'] = None
    for index, row in vocab.iterrows():
        pfl.to_mp3(row['WORD'])
        vb = add_vocabbulary(
            word=row['WORD'],
            pronunciation=row['PRONUNCIATION'],
            part_of_speech=row['PART_OF_SPEECH'],
            meaning=row['MEANING'],
            topic_name=row['TOPIC_NAME'],
            image_link=row['IMAGE_LINK'],
            cert_level_name=row['CERT_LEVEL_NAME'],
            id_token=user['idToken']  # Truyền id_token đúng cách
        )
        vocab.at[index, 'VOCAB_ID'] = vb['VOCAB_ID']
    pfl.write_to_file(vocab, file)
    print("Add vocabulary successfully")


def add_range_topic(file):
    top = pfl.read_csv(file)
    top = pfl.dataframe_nan_to_none(top)
    top['TOPIC_ID'] = None
    for index, row in top.iterrows():
        topic = add_topic(topic_name=row['TOPIC_NAME'], description=row['DESCRIPTION'], image_link=row['DESCRIPTION'])
        top.at[index, 'TOPIC_ID'] = topic['TOPIC_ID']
    pfl.write_to_file(top, file)
    print("Add topic successfully")


def add_range_lesson(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    data['LESSON_ID'] = None
    for index, row in data.iterrows():
        les = add_lesson(name_of_lesson=row['NAME_OF_LESSON'], image_link=row['IMAGE_LINK'],
                         is_vocab=row['IS_VOCAB'], order=row['ORDER'], topic_name=row['TOPIC_NAME'],
                         cert_level_name=row['CERT_LEVEL_NAME'])
        data.at[index, 'LESSON_ID'] = les['LESSON_ID']
    pfl.write_to_file(data, file)
    print("Add lesson successfully")


def add_range_test_type(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    # map old id to new id
    map_dict = {}
    for index, row in data.iterrows():
        row_test_type = add_test_type(name_of_test_type=row['NAME_OF_TEST_TYPE'], total_duration=row['TOTAL_DURATION'],
                      maximum=row['MAXIMUM_SCORE'], cert_level_name=row['CERT_LEVEL_NAME'])
        map_dict[row['TEST_TYPE_ID']] = row_test_type['TEST_TYPE_ID']
        data.at[index, 'TEST_TYPE_ID'] = row_test_type['TEST_TYPE_ID']
    pfl.write_to_file(data, file)
    # update test id in test
    test = pfl.read_csv('data/csv/test.csv')
    test = pfl.dataframe_nan_to_none(test)
    test = pfl.map_new_value(test, 'TEST_TYPE_ID', map_dict)
    pfl.write_to_file(test, 'data/csv/test.csv')
    print("Add test type successfully")
    # map



def add_range_part_of_test(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    map_dict = {}
    for index, row in data.iterrows():
        part = add_part_of_test(name_of_part=row['NAME_OF_PART'], test_type_name=row['TEST_TYPE_NAME'])
        map_dict[row['PART_OF_TEST_ID']] = part['PART_OF_TEST_ID']
    # map part of test id
    part_detail = pfl.read_csv('data/csv/part_detail.csv')
    part_detail = pfl.dataframe_nan_to_none(part_detail)
    part_detail = pfl.map_new_value(part_detail, 'PART_OF_TEST_ID', map_dict)
    pfl.write_to_file(part_detail, 'data/csv/part_detail.csv')
    pfl.write_to_file(pfl.map_new_value(data, 'PART_OF_TEST_ID', map_dict), file)
    print("Add part of test successfully")


def add_range_cert_level(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    data['CERT_LEVEL_ID'] = None
    for index, row in data.iterrows():
        cert = add_cert_level(level_name=row['CERT_LEVEL_NAME'], description=row['DESCRIPTION'],
                              image_link=row['IMAGE_LINK'])
        data.at[index, 'CERT_LEVEL_ID'] = cert['CERT_LEVEL_ID']
    pfl.write_to_file(data, file)
    print("Add cert level successfully")


def add_range_question_type(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    map_dict = {}
    for index, row in data.iterrows():
        question_type = add_question_type(name_of_type=row['NAME_OF_TYPE'])
        map_dict[row['QUESTION_TYPE_ID']] = question_type['QUESTION_TYPE_ID']
        data.at[index, 'QUESTION_TYPE_ID'] = question_type['QUESTION_TYPE_ID']
    pfl.write_to_file(data, file)
    # map question type id
    question = pfl.read_csv('data/csv/question.csv')
    question = pfl.dataframe_nan_to_none(question)
    question = pfl.map_new_value(question, 'QUESTION_TYPE_ID', map_dict)
    pfl.write_to_file(question, 'data/csv/question.csv')
    print('Add question type successfully')


def add_range_user(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    # data['BIRTH_OF_DATE'] = pd.to_datetime(data['BIRTH_OF_DATE'], format='%b,%d,%Y')
    # data['CREATED_DATE'] = pd.to_datetime(data['CREATED_DATE'], format='%b,%d,%Y')
    # data['PHONE_NUMBER'] = data['PHONE_NUMBER'].apply(lambda x: str(x))
    # data['PHONE_NUMBER'] = data['PHONE_NUMBER'].str.replace('-', '', regex=False)
    data['USER_ID'] = None
    for index, row in data.iterrows():
        _user = add_user(
            full_name=row['FULL_NAME'],
            age=row['AGE'],
            gender=row['GENDER'],  # Sửa lỗi cú pháp ở đây
            birth_of_date=row['BIRTH_OF_DATE'],
            email=row['EMAIL'],
            phone_number=row['PHONE_NUMBER'],
            user_name=row['USER_NAME'],
            created_date=row['CREATED_DATE'],
            password=row['PASSWORD'],
            is_active=row['IS_ACTIVE']
        )
        data.at[index, 'USER_ID'] = _user['USER_ID']
    pfl.write_to_file(data, file)
    print('Add user successfully')


def add_range_test(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    map_dict = {}
    for index, row in data.iterrows():
        test = add_test(name_of_test=row['NAME_OF_TEST'], test_type_id=row['TEST_TYPE_ID'])
        map_dict[row['TEST_ID']] = test['TEST_ID']
        data.at[index, 'TEST_ID'] = test['TEST_ID']
    pfl.write_to_file(data, file)
    # map test id
    part_detail = pfl.read_csv('data/csv/part_detail.csv')
    part_detail = pfl.dataframe_nan_to_none(part_detail)
    part_detail = pfl.map_new_value(part_detail, 'TEST_ID', map_dict)
    pfl.write_to_file(part_detail, 'data/csv/part_detail.csv')

    question = pfl.read_csv('data/csv/question.csv')
    question = pfl.dataframe_nan_to_none(question)
    question = pfl.map_new_value(question, 'TEST_ID', map_dict)
    pfl.write_to_file(question, 'data/csv/question.csv')

    print('Add test successfully')


def add_range_part_detail(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    map_dict = {}
    for index, row in data.iterrows():
        detail = add_part_detail(part_of_test_id=row['PART_OF_TEST_ID'],
                                 test_id=row['TEST_ID'],
                                 audio_link=row['AUDIO_LINK'],
                                 image_link=row['IMAGE_LINK'],
                                 order=row['ORDER'])
        map_dict[row['PART_DETAIL_ID']] = detail['PART_DETAIL_ID']

    data=pfl.map_new_value(data, 'PART_DETAIL_ID', map_dict)
    pfl.write_to_file(data, file)
    # map part detail id
    question = pfl.read_csv('data/csv/question.csv')
    question = pfl.dataframe_nan_to_none(question)
    question = pfl.map_new_value(question, 'PART_DETAIL_ID', map_dict)
    pfl.write_to_file(question, 'data/csv/question.csv')

    print('Add part detail successfully')


def add_range_question_content(file):
    data = pfl.read_csv(file)
    data = pfl.dataframe_nan_to_none(data)
    map_dict = {}
    for index, row in data.iterrows():
        content = add_question_content(content=row['CONTENT'])
        map_dict[row['QUESTION_CONTENT_ID']] = content['QUESTION_CONTENT_ID']
        data.at[index, 'QUESTION_CONTENT_ID'] = content['QUESTION_CONTENT_ID']
    pfl.write_to_file(data, file)
    # map question content id
    question = pfl.read_csv('data/csv/question.csv')
    question = pfl.dataframe_nan_to_none(question)
    question = pfl.map_new_value(question, 'QUESTION_CONTENT_ID', map_dict)
    pfl.write_to_file(question, 'data/csv/question.csv')
    print('Add question content successfully')


def add_range_question_and_answer(question_file, answer_file):
    gmail, password = 'eng2UTC@gmail.com', 'eng2UTC@123'
    user = authenticate_user(gmail, password)
    question = pfl.read_csv(question_file)
    answer = pfl.read_csv(answer_file)
    question = pfl.dataframe_nan_to_none(question)
    answer = pfl.dataframe_nan_to_none(answer)
    map_question_id = {}
    for index, row in question.iterrows():
        ques = add_question(content=row['CONTENT'], audio_link=row['AUDIO_LINK'], image_link=row['IMAGE_LINK'],
                            correct_anwer_id=row['CORRECT_ANSWER_ID'], question_type_id=row['QUESTION_TYPE_ID'],
                            part_detail_id=row['PART_DETAIL_ID'], question_content_id=row['QUESTION_CONTENT_ID'],
                            order=row['ORDER'], test_id=row['TEST_ID'])
        # replace question_id in answer
        map_question_id[row['QUESTION_ID']] = ques['QUESTION_ID']
        question.at[index, 'QUESTION_ID'] = ques['QUESTION_ID']
    # map question id
    answer = pfl.map_new_value(answer, 'QUESTION_ID', map_question_id)
    print('Add question successfully')
    map_answer_id = {}
    for index, row in answer.iterrows():
        ans=add_answer(content=row['CONTENT'], image=row['IMAGE'], audio_link=row['AUDIO_LINK'],
                   question_id=row['QUESTION_ID'], id_token=user['idToken'])
        map_answer_id[row['ANSWER_ID']] = ans['ANSWER_ID']
        answer.at[index, 'ANSWER_ID'] = ans['ANSWER_ID']
    print('Add answer successfully')
    # update new id for correct answer and update question
    question=pfl.map_new_value(question, 'CORRECT_ANSWER_ID', map_answer_id)

    for index, row in question.iterrows():
        update_anwser_in_question(row['QUESTION_ID'])
        update_correct_anser(row['QUESTION_ID'], row['CORRECT_ANSWER_ID'])

    pfl.write_to_file(question, question_file)
    pfl.write_to_file(answer, answer_file)
    print('Update correct answer id successfully')


# update
def update_questions_in_test(test_id, questions):
    # add them attribute question vào exisiting test
    db.reference('TEST').child(test_id).update({'QUESTIONS': questions})
    print('Add questions in test successfully')

def update_correct_anser(question_id, new_correct_answer_id):
    db.reference('QUESTION').child(question_id).update({'CORRECT_ANSWER_ID': new_correct_answer_id})

def update_anwser_in_question(question_id):
    # add them attribute question vào exisiting test
    try:
        answers = db.reference('ANSWER').order_by_child('QUESTION_ID').equal_to(question_id).get()
    except Exception as e:
        print(f"Error get answers in question: {e}")
    try:
        db.reference('QUESTION').child(question_id).update({'ANSWERS': answers})
    except Exception as e:
        print(f"Error update answers in question: {e}")
    print('Add answers in question successfully')



if __name__ == '__main__':
    # add_range_user('data/csv/user.csv')
    # add_range_cert_level('data/csv/cert_level.csv')
    # add_range_topic('data/csv/topic.csv')
    # add_range_vocab('data/csv/vocabulary_A1.csv')
    # add_range_vocab('data/csv/vocabulary_A2.csv')
    # add_range_lesson('data/csv/lesson.csv')
    # add_range_test_type('data/csv/test_type.csv')
    # add_range_part_of_test('data/csv/part_of_test.csv')
    # add_range_test('data/csv/test.csv')
    add_range_part_detail('data/csv/part_detail.csv')
    # add_range_question_type('data/csv/question_type.csv')
    # add_range_question_content('data/csv/question_content.csv')
    add_range_question_and_answer('data/csv/question.csv', 'data/csv/answer.csv')

    # detail=pfl.read_csv('data/csv/part_detail.csv')
    # detail=pfl.dataframe_nan_to_none(detail)
    # ques=pfl.read_csv('data/csv/question.csv')
    # ques=pfl.dataframe_nan_to_none(ques)
    # i=0
    # for j in range(40):
    #     if j!=0 and j%5==0:
    #         i+=1
    #     ques.at[j, 'PART_DETAIL_ID'] = detail.at[i, 'PART_DETAIL_ID']
    #
    # pfl.write_to_file(ques, 'data/csv/question.csv')


    pass
