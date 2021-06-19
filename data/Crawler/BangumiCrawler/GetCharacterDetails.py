#!/usr/bin/env python
#-*- coding:UTF-8 -*-

import pandas as pd
from bs4 import BeautifulSoup
import re
import requests

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

def getNonRepeatList(data):
    new_data = []
    for i in range(len(data)):
        if data[i] not in new_data:
            new_data.append(data[i])
    return new_data

def getname(soup):
    result = ""
    try:
        a = soup.find_all("span", class_="tip", text=re.compile(r'简体中文名:'))
        for i in a:
            result = i.parent.text.split()[1]
    except:
        result = ""
    if result=="":
        try:
            a = soup.find_all(class_="nameSingle")
            for i in a:
                result = i.text
        except:
            result=""
        result = result[1:]
    return result

def getgender(soup):
    result = ""
    try:
        a = soup.find_all("span", class_="tip", text=re.compile(r'性别:'))
        for i in a:
            result = i.parent.text.split()[1]
    except:
        result = ""
    return result

def getbirthday(soup):
    result = ""
    try:
        a = soup.find_all("span", class_="tip", text=re.compile(r'生日:'))
        for i in a:
            result = i.parent.text.split()[1]
    except:
        result = ""
    return result

def getheight(soup):
    result = ""
    try:
        a = soup.find_all("span", class_="tip", text=re.compile(r'身高:'))
        for i in a:
            result = i.parent.text.split()[1]
    except:
        result = ""
    return result

def getweight(soup):
    result = ""
    try:
        a = soup.find_all("span", class_="tip", text=re.compile(r'体重:'))
        for i in a:
            result = i.parent.text.split()[1]
    except:
        result = ""
    return result

def getbloodtype(soup):
    result = ""
    try:
        a = soup.find_all("span", class_="tip", text=re.compile(r'血型:'))
        for i in a:
            result = i.parent.text.split()[1]
    except:
        result = ""
    return result

def getworks(soup):
    result = []
    try:
        a = soup.find_all(href=re.compile("/subject/"), class_="l")
        for i in a:
            result.append(i.text)
    except:
        result = []
    return getNonRepeatList(result)

def getcv(soup):
    result = []
    try:
        a = soup.find_all(href=re.compile("/person/"), class_="l")
        for i in a:
            result.append(i.text)
    except:
        result = []
    return getNonRepeatList(result)

def getdescription(soup):
    result = ""
    try:
        a = soup.find_all(class_="detail")
        for i in a:
            result = i.text
    except:
        result = ""
    return result

def getdetail(x):
    name, gender, birthday, height, weight, bloodtype, works, cv, description = '', '', '', '', '', '', '', '', ''
    global count
    try:
        r = requests.get(x['Link'], headers=headers, timeout=20)
        r.encoding = 'UTF-8'
    except:
        print('服务器无响应1')
        try:
            r = requests.get(x['Link'], headers=headers, timeout=10)
            r.encoding = 'UTF-8'
        except:
            print('服务器无响应2')
            try:
                r = requests.get(x['Link'], headers=headers, timeout=10)
                r.encoding = 'UTF-8'
            except:
                print('服务器无响应3')
    try:
        soup = BeautifulSoup(r.text, 'lxml')
        name = getname(soup)
        gender = getgender(soup)
        birthday = getbirthday(soup)
        height = getheight(soup)
        weight = getweight(soup)
        bloodtype = getbloodtype(soup)
        works = getworks(soup)
        cv = getcv(soup)
        description = getdescription(soup)
        print('已完成: '+str(name)+' 第'+str(count)+'个')
    except:
        print('Error！！ 未完成: ' + str(name) + ' 第' + str(count) + '个')
    count += 1
    return name, gender, birthday, height, weight, bloodtype, works, cv, description

if __name__ == "__main__":
    count = 1
    df1 = pd.read_excel('Link_Id.xlsx')
    df1['详细'] = df1.apply(lambda x: getdetail(x), axis=1)
    df1['名字'] = df1.apply(lambda x: x['详细'][0], axis=1)
    df1['性别'] = df1.apply(lambda x: x['详细'][1], axis=1)
    df1['生日'] = df1.apply(lambda x: x['详细'][2], axis=1)
    df1['身高'] = df1.apply(lambda x: x['详细'][3], axis=1)
    df1['体重'] = df1.apply(lambda x: x['详细'][4], axis=1)
    df1['血型'] = df1.apply(lambda x: x['详细'][5], axis=1)
    df1['作品'] = df1.apply(lambda x: x['详细'][6], axis=1)
    df1['声优'] = df1.apply(lambda x: x['详细'][7], axis=1)
    df1['介绍'] = df1.apply(lambda x: x['详细'][8], axis=1)
    df1.to_excel('Data.xlsx')

    # r = requests.get('https://bangumi.tv/character/26', headers=headers)
    # r.encoding="UTF-8"
    # soup = BeautifulSoup(r.text, 'lxml')
    # a = soup.find_all(class_="nameSingle")
    # for i in a:
    #     print(i.text)
        # print(i.parent.parent.text.split()[1])
        # print(i.parent)
        # print(i.parent.parent.text.split()[1])