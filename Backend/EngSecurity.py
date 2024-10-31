import bcrypt

def hash_bcrypt(data: str) -> str:
    """
    Hash data using bcrypt algorithm

    :param data: Data to hash
    :return: Hashed data
    """
    return bcrypt.hashpw(data.encode(), bcrypt.gensalt()).decode()

def verify_bcrypt(data: str, hashed: str) -> bool:
    """
    Verify data with hashed data using bcrypt algorithm

    :param data: Data to verify
    :param hashed: Hashed data
    :return: True if data is verified, False otherwise
    """
    return bcrypt.checkpw(data.encode(), hashed.encode())

