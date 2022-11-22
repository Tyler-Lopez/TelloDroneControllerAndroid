import boto3
import os
import sys
import uuid
from urllib.parse import unquote_plus
from PIL import Image, ImageOps
import PIL.Image

s3_client = boto3.client('s3')

def resize_image(image_path, resized_path):
  image = Image.open(image_path)
  image = ImageOps.exif_transpose(image) # Apply EXIF transformation
  image.thumbnail(tuple(x / 2 for x in image.size))
  image.save(resized_path)

def handler(event, context):
  for record in event['Records']:
    bucket = record['s3']['bucket']['name']
    key = unquote_plus(record['s3']['object']['key'])
    file_key = key.split('/')[-1] # Get last string element in the list, which should be <username>.jpg
    tmpkey = key.replace('/', '')
    download_path = '/tmp/{}{}'.format(uuid.uuid4(), tmpkey)
    upload_path = '/tmp/resized-{}'.format(tmpkey)
    s3_client.download_file(bucket, key, download_path)
    resize_image(download_path, upload_path)
    print('resized-{}'.format(key))
    s3_client.upload_file(upload_path, bucket, 'public/profile_picture/{}'.format(file_key))