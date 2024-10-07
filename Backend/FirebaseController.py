import firebase_admin
from firebase_admin import credentials, db
from datetime import datetime

# Khởi tạo Firebase Admin SDK
def connect_firebase():
    cred = credentials.Certificate('serviceAccountKey.json')  # Đường dẫn đến tệp JSON xác thực của bạn
    firebase_admin.initialize_app(cred, {
        'databaseURL': 'https://eng2utc-default-rtdb.firebaseio.com/'  # Thay bằng tên cơ sở dữ liệu của bạn
    })

def add_user(user_name, gender, is_active, image_link, password):
    # Tạo dữ liệu người dùng
    user_data = {
        'USER_NAME': user_name,
        'GENDER': gender,
        'IS_ACTIVE': is_active,
        'CREATED_DATE': datetime.now().isoformat(),  # Thời gian hiện tại dưới dạng chuỗi ISO
        'IMAGE_LINK': image_link,
        'PASSWORD': password,  # Hãy đảm bảo mã hóa mật khẩu trước khi lưu
    }

    # Thêm người dùng vào Realtime Database
    user_ref = db.reference('USER').push(user_data)  # Sử dụng push() để tạo ID ngẫu nhiên
    print(f"User added successfully with ID: {user_ref.key}")

if __name__ == '__main__':
    connect_firebase()  # Kết nối với Firebase

    # Thêm người dùng
    # add_user('ChungLe', 1, True, 'https://firebasestorage.googleapis.com/v0/b/eng2utc.appspot.com/o/HoaHong.png?alt=media&token=49612cee-03f1-4072-b2f6-6a951f38e98e', 'ChungLe')
    # add_user('TuanKhai', 1, False, 'https://firebasestorage.googleapis.com/v0/b/eng2utc.appspot.com/o/HoaHong.png?alt=media&token=49612cee-03f1-4072-b2f6-6a951f38e98e', 'TuanKhai')
    # add_user('ChienTran', 1, True, 'https://firebasestorage.googleapis.com/v0/b/eng2utc.appspot.com/o/HoaHong.png?alt=media&token=49612cee-03f1-4072-b2f6-6a951f38e98e', 'ChienTran')
    # add_user('LamBao', 1, True, 'https://firebasestorage.googleapis.com/v0/b/eng2utc.appspot.com/o/HoaHong.png?alt=media&token=49612cee-03f1-4072-b2f6-6a951f38e98e', 'LamBao')
    # add_user('BackNguyen', 1, True, 'https://firebasestorage.googleapis.com/v0/b/eng2utc.appspot.com/o/HoaHong.png?alt=media&token=49612cee-03f1-4072-b2f6-6a951f38e98e', 'BackNguyen')
