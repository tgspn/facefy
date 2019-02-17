import json
import requests

ip = "10.10.0.186"
porta = "8081"

def get_account_info(headers,event_id):
    url = 'http://'+ip+':'+porta+'/event/'+event_id
    #api_url = '{0}account'.format(api_url_base)
    try:
        response = requests.get(url, headers=headers)
        print(response.status_code)
        if response.status_code == 200:
            return json.loads(response.content.decode('utf-8'))
        else:
            return None
    except:
        print('get_account_info, erro na url:',url)


def send_confirm_buy(event_id,headers):
    try:
        url = 'http://'+ip+':'+porta+'/cashiers/'+event_id
        url = url+"/"+id_do_evento+"?amount="+valor_pagar+"&description="+descricao
        r = requests.post(url, data = {},headers=headers)
    except:
        print('send_confirm_buy, erro na url:',url)


# event_id = "7ffeb450-c571-4bfe-95cc-362b744d8e06"
# headers = {'Content-Type': 'application/json','Master-Key': 'facefyiscool'}
# print(get_account_info(headers,event_id))

# id_do_carinha = 1
# headers= {'Custumer-Id':id_do_carinha,'Content-Type':'application/json'}
# send_confirm_buy(event_id,headers)