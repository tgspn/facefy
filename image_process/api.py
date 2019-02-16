import json
import requests


def get_account_info():

    api_url = '{0}account'.format(api_url_base)

    response = requests.get(api_url, headers=headers)

    if response.status_code == 200:
        return json.loads(response.content.decode('utf-8'))
    else:
        return None

api_token = 'your_api_token'
event_id = "1"
api_url_base = 'http://10.10.0.186:8081/events/custumers/'+event_id
headers = {'Content-Type': 'application/json','Master-Key': 'facefyiscool'}

