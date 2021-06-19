#!/usr/bin/env python
#-*- coding:UTF-8 -*-
import requests
from bs4 import BeautifulSoup
import re
import pandas as pd

n = 2

path = 'Link_Id.xlsx'

headers = {
    'Connection': 'keep-alive',
    'Cache-Control': 'max-age=0',
    'sec-ch-ua': '" Not;A Brand";v="99", "Google Chrome";v="91", "Chromium";v="91"',
    'sec-ch-ua-mobile': '?0',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'Sec-Fetch-Site': 'same-origin',
    'Sec-Fetch-Mode': 'navigate',
    'Sec-Fetch-User': '?1',
    'Sec-Fetch-Dest': 'document',
    'Accept-Language': 'zh-CN,zh;q=0.9',
}

def getlist(n):
    linklist=[]
    for pagenum in range(1,n):
        r = requests.get('https://bangumi.tv/character?gender=2&page=%d'%pagenum,headers=headers)
        soup = BeautifulSoup(r.text, 'lxml')
        soups= soup.find_all(href=re.compile(r"/character/"),class_="avatar")
        for i in soups:
            i = i.attrs
            i = i['href']
            link = re.search('/character/(\d*)',i).group()
            link = "https://bangumi.tv" + link
            linklist.append(link)
        print('已完成女性'+str(pagenum)+'页,目前共'+str(len(linklist)))
    for pagenum in range(1,n):
        r = requests.get('https://bangumi.tv/character?gender=1&page=%d'%pagenum,headers=headers)
        soup = BeautifulSoup(r.text, 'lxml')
        soups= soup.find_all(href=re.compile(r"/character/"),class_="avatar")
        for i in soups:
            i = i.attrs
            i = i['href']
            link = re.search('/character/(\d*)',i).group()
            link = "https://bangumi.tv" + link
            linklist.append(link)
        print('已完成男性'+str(pagenum)+'页,目前共'+str(len(linklist)))
    return linklist

def getdf(n):
    linklist = getlist(n)
    df = pd.DataFrame(list(zip(linklist)),
               columns =['Link'])
    return df

if __name__ == "__main__":
    df = getdf(n)
    df.to_excel(path)