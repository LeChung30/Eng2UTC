import os
import eng_to_ipa as ipa
import librosa
import pandas as pd
import pyttsx3
import requests
import matplotlib.pyplot as plt
import sounddevice as sd

# Tạo đối tượng pyttsx3
engine = pyttsx3.init()

def read_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        data = f.readlines()
    return data

def write_to_file(data, file_path):
    df = pd.DataFrame(data)
    df.to_csv(file_path, index=False, header=True)

def get_ipa(text):
    text = text.replace('-', ' ')
    ipa_text = ipa.convert(text)
    return ipa_text

def save_ipas_of_words():
    res = []
    words = read_file('word.txt')
    for i in words:
        res.append(get_ipa(i))
    write_to_file(res, 'ipa.txt')

def read_csv(file_path):
    return pd.read_csv(file_path, encoding='utf-8')

def read_excel(file_path):
    return pd.read_excel(file_path)

def to_mp3(text, voice_id=1, rate=150, volume=1):
    directory = 'data/mp3'
    if not os.path.exists(directory):
        os.makedirs(directory)

    # Thay thế các ký tự không hợp lệ trong tên file
    file_name = text.replace(" ", "_").replace("/", "_")
    file_path = os.path.join(directory, file_name + '.mp3')

    voices = engine.getProperty('voices')
    engine.setProperty('voice', voices[voice_id].id)  # Chọn giọng nói
    engine.setProperty('rate', rate)  # Tùy chỉnh tốc độ
    engine.setProperty('volume', volume)  # Tùy chỉnh âm lượng

    engine.save_to_file(text, file_path)
    engine.runAndWait()  # Chạy engine để hoàn thành quá trình

def speak(text, voice_id=1, rate=150, volume=1):
    voices = engine.getProperty('voices')
    engine.setProperty('voice', voices[voice_id].id)  # Chọn giọng nữ (thường là giọng thứ 2)
    # Tùy chỉnh tốc độ
    engine.setProperty('rate', rate)  # Tốc độ đọc
    # Tùy chỉnh âm lượng
    engine.setProperty('volume', volume)
    engine.say(text)
    engine.runAndWait()

def voice_list():
    voices = engine.getProperty('voices')
    for voice in voices:
        print(f'ID: {voice.id} and Name: {voice.name}')

def nan_to_none(value):
    if pd.isna(value):
        return None
    return value

def dataframe_nan_to_none(df):
    return df.applymap(nan_to_none)

def load_mp3(local_path):
    # load mp3 to librosa
    y, sr = librosa.load(local_path)
    return y, sr  # y: audio time series, sr: sampling rate of y

def sound_plot(y, sr):
    # Play audio sound librosa
    librosa.display.waveshow(y, sr=sr)
    plt.xlabel('Time (s)')
    plt.ylabel('Amplitude')
    plt.show()

def play_sound(y, sr):
    sd.play(y, sr)
    sd.wait()

def download_audio_firestorage(url_link, local_path):
    response = requests.get(url_link)
    if response.status_code == 200:
        with open(local_path, 'wb') as file:
            file.write(response.content)
        print("File downloaded successfully!")
    else:
        print(f"Failed to download file. Status code: {response.status_code}")

if __name__ == '__main__':
    file='data/data.txt'
    df=read_csv(file)
    ipas=[]
    for i in range(len(df)):
        text=df['word'][i]
        ipas.append(get_ipa(text))
    write_to_file(ipas,'data/ipa.txt')