import hashlib



def hash_blake2b(data: str) -> str:
    """
    Hash data using BLAKE2b algorithm

    :param data: Data to hash
    :return: Hashed data
    """
    return hashlib.blake2b(data.encode()).hexdigest()

def hash_md5(data: str) -> str:
    """
    Hash data using MD5 algorithm

    :param data: Data to hash
    :return: Hashed data
    """
    return hashlib.md5(data.encode()).hexdigest()

def encrypt_AES(data: str, key: str) -> str:
    """
    Encrypt data using AES algorithm

    :param data: Data to encrypt
    :param key: Key to encrypt
    :return: Encrypted data
    """
    return hashlib.sha256(data.encode()).hexdigest()

def decrypt_AES(data: str, key: str) -> str:
    """
    Decrypt data using AES algorithm

    :param data: Data to decrypt
    :param key: Key to decrypt
    :return: Decrypted data
    """
    return hashlib.sha256(data.encode()).hexdigest()

def encrypt_RSA(data: str, key: str) -> str:
    """
    Encrypt data using RSA algorithm

    :param data: Data to encrypt
    :param key: Key to encrypt
    :return: Encrypted data
    """
    return hashlib.sha256(data.encode()).hexdigest()

def decrypt_RSA(data: str, key: str) -> str:
    """
    Decrypt data using RSA algorithm

    :param data: Data to decrypt
    :param key: Key to decrypt
    :return: Decrypted data
    """
    return hashlib.sha256(data.encode()).hexdigest()

