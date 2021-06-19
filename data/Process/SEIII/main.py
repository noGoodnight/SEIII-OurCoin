import csv
import re
from langconv import *

def cht_to_chs(line):
    line = Converter('zh-hans').convert(line)
    line.encode('utf-8')
    return line

def clean_array(arr):
    new_arr=[]
    for i in range(len(arr)):
        if("：" in arr[i] or ":" in arr[i]):
            new_arr.append(arr[i].replace("：", ":").replace(" ", "").replace("（", "(").replace("）", ")"))
    return new_arr

'''bangumi_name=1'''
'''character_name:cv_name=11'''
if __name__ == '__main__':
    eid = 1
    rid = 1
    character_dic = {}
    cv_dic = {}
    character_cv = []
    director_dic = {}
    creator_dic = {}

    # with open("default.dic", "w") as d:
    #     with open("sql.txt", "w") as t:
    #         t.write("USE seiii;\n")
    #         t.write("insert into `user` VALUES ('1','test','test');\n")
    #         t.write("insert into `graph` VALUES ('1','test_graph_1','1');\n")
    #         with open('data.csv', 'r') as f:
    #             reader = csv.reader(f)
    #             for row in reader:
    #
    #                 d.write(row[1]+"\t"+"nz\t"+"1\n")
    #
    #                 if(len(row[11])==0): continue
    #                 cv_raw = row[11]
    #                 if (cv_raw[0] == ","):
    #                     cv_raw = cv_raw[1:]
    #                 cv_raw = cv_raw + ','
    #                 cv_list = clean_array(re.findall('(.*?),', cv_raw))
    #                 if (len(cv_list) == 0): continue
    #
    #                 bangumi = eid
    #                 t.write("insert into `entity` VALUE (" + str(eid) + ",1,'" + row[1] + "','100','100','animation','#3455EA');\n")
    #                 eid += 1
    #
    #                 for item in cv_list:
    #                     has_character = True
    #                     has_cv = True
    #                     character = item[0:item.find(":")]
    #                     character.replace(' ','').replace('（','(').replace('）',')').replace('。','.').replace('，',',')
    #                     cv = item[item.find(":")+1:]
    #                     cv.replace(' ', '').replace('（', '(').replace('）', ')').replace('。', '.').replace('，',',').replace('t', '')
    #                     if(character=="旁白"): continue
    #                     if('(' in character): character = character[0:character.find("(")]
    #                     character = cht_to_chs(character)
    #                     cv = cht_to_chs(cv)
    #
    #                     if(not (character in character_dic.keys())):
    #                         t.write("insert into `entity` VALUE (" + str(eid) + ",1,'" + character + "','100','100','person','#3455EA');\n")
    #                         character_dic[character] = eid
    #                         eid += 1
    #                         has_character = False
    #
    #                         d.write(character+"\tnr\t2\n")
    #
    #                     if(not (cv in cv_dic.keys())):
    #                         t.write("insert into `entity` VALUE (" + str(eid) + ",1,'" + cv + "','100','100','actor','#3455EA');\n")
    #                         cv_dic[cv] = eid
    #                         eid += 1
    #                         has_cv = False
    #
    #                         d.write(cv+"\tnr\t2\n")
    #
    #                     t.write("insert into `relation` VALUE (" + str(rid) + ",'出演',1," + str(character_dic[character]) + "," + str(bangumi) + ");\n")
    #                     rid+=1
    #                     if(not(has_character and has_cv)):
    #                         t.write("insert into `relation` VALUE (" + str(rid) + ",'配音',1," + str(cv_dic[cv]) + "," + str(character_dic[character]) + ");\n")
    #                         rid+=1

    # with open("sql.txt", "w") as t:
    #     t.write("USE seiii;\n")
    #     t.write("insert into `user` VALUES ('1','test','test');\n")
    #     t.write("insert into `graph` VALUES ('1','test_graph_1','1');\n")
    #     with open('data.csv', 'r') as f:
    #         reader = csv.reader(f)
    #         for row in reader:
    #
    #             #bangumi
    #             bangumi = eid
    #             t.write("insert into `entity` VALUE (" + str(eid) + ",1,'" + row[
    #                 1] + "','100','100','animation','#F4590C');\n")
    #             eid += 1
    #
    #             #bangumi staff
    #             staff_raw = row[12]
    #             if(len(staff_raw)!=0):
    #                 if(staff_raw[0] == ","): staff_raw = staff_raw[1:]
    #                 staff_raw = staff_raw + ','
    #                 staff_list = clean_array(re.findall('(.*?),', staff_raw))
    #                 if (len(staff_list) != 0):
    #                     for item in staff_list:
    #                         if(":" not in item):
    #                             continue
    #                         type = item[0:item.find(":")]
    #                         name = item[item.find(":") + 1:]
    #                         type = cht_to_chs(type)
    #                         name = cht_to_chs(name)
    #                         if(type=="导演" or type=="监督"):
    #                             director = name
    #                             if ('/' in director): director = director[0:director.find('/')]
    #                             if ('(' in director): director = director[0:director.find('(')]
    #                             if (not (director in director_dic.keys())):
    #                                 t.write("insert into `entity` VALUE (" + str(
    #                                     eid) + ", 1,'" + director + "','100','100','director','#3455EA');\n")
    #                                 director_dic[director] = eid
    #                                 eid += 1
    #                                 t.write("insert into `relation` VALUE (" + str(rid) + ",'导演',1," + str(
    #                                     director_dic[director]) + "," + str(bangumi) + ");\n")
    #                                 rid += 1
    #                             else:
    #                                 t.write("insert into `relation` VALUE (" + str(rid) + ",'导演',1," + str(
    #                                     director_dic[director]) + "," + str(bangumi) + ");\n")
    #                                 rid += 1
    #                         if(type=="动画制作" or type=="制作"):
    #                             creator = name
    #                             if ('/' in creator): creator = creator[0:creator.find('/')]
    #                             if ('(' in creator): creator = creator[0:creator.find('(')]
    #                             if (not (creator in creator_dic.keys())):
    #                                 t.write("insert into `entity` VALUE (" + str(
    #                                     eid) + ", 1,'" + creator + "','100','100','company','#3455EA');\n")
    #                                 creator_dic[creator] = eid
    #                                 eid += 1
    #                                 t.write("insert into `relation` VALUE (" + str(rid) + ",'制作',1," + str(
    #                                     creator_dic[creator]) + "," + str(bangumi) + ");\n")
    #                                 rid += 1
    #                             else:
    #                                 t.write("insert into `relation` VALUE (" + str(rid) + ",'制作',1," + str(
    #                                     creator_dic[creator]) + "," + str(bangumi) + ");\n")
    #                                 rid += 1
    #
    #             #bangumi detail
    #             bilibili_url = row[8]
    #             jpg_url = row[3]
    #             description = row[9]
    #             tags = row[10]
    #             t.write("insert into `detail` VALUE (" + str(bangumi) + ", '" + bilibili_url + "', '" + jpg_url + "', '" + description + "', '" + tags + "');\n")
    #
    #             #character+cv
    #             if (len(row[11]) == 0): continue
    #             cv_raw = row[11]
    #             if (cv_raw[0] == ","):
    #                 cv_raw = cv_raw[1:]
    #             cv_raw = cv_raw + ','
    #             cv_list = clean_array(re.findall('(.*?),', cv_raw))
    #             if (len(cv_list) == 0): continue
    #             for item in cv_list:
    #                 has_character = True
    #                 has_cv = True
    #                 character = item[0:item.find(":")]
    #                 character.replace(' ', '').replace('（', '(').replace('）', ')').replace('。', '.').replace('，', ',')
    #                 cv = item[item.find(":") + 1:]
    #                 cv.replace(' ', '').replace('（', '(').replace('）', ')').replace('。', '.').replace('，', ',').replace(
    #                     't', '')
    #                 if (character == "旁白"): continue
    #                 if ('(' in character): character = character[0:character.find("(")]
    #                 character = cht_to_chs(character)
    #                 cv = cht_to_chs(cv)
    #
    #                 if (not (character in character_dic.keys())):
    #                     t.write("insert into `entity` VALUE (" + str(
    #                         eid) + ",1,'" + character + "','100','100','person','#3455EA');\n")
    #                     character_dic[character] = eid
    #                     eid += 1
    #                     has_character = False
    #
    #                 if (not (cv in cv_dic.keys())):
    #                     t.write("insert into `entity` VALUE (" + str(
    #                         eid) + ",1,'" + cv + "','100','100','actor','#45F110');\n")
    #                     cv_dic[cv] = eid
    #                     eid += 1
    #                     has_cv = False
    #
    #                 if(has_character and has_cv):
    #                     done = False
    #                     for pair in character_cv:
    #                         if(character==pair.split(" ")[0] and cv==pair.split(" ")[1]):
    #                             t.write("insert into `relation` VALUE (" + str(rid) + ",'出演',1," + str(
    #                                 pair.split(" ")[2]) + "," + str(bangumi) + ");\n")
    #                             rid += 1
    #                             done = True
    #                             break
    #                     if(not done):
    #                         t.write("insert into `entity` VALUE (" + str(
    #                             eid) + ",1,'" + character + "','100','100','person','#3455EA');\n")
    #                         character_cv.append(character + " " + cv + " " + str(eid))
    #                         t.write("insert into `relation` VALUE (" + str(rid) + ",'出演',1," + str(
    #                             eid) + "," + str(bangumi) + ");\n")
    #                         rid += 1
    #                         t.write(
    #                             "insert into `relation` VALUE (" + str(rid) + ",'配音',1," + str(cv_dic[cv]) + "," + str(
    #                                 eid) + ");\n")
    #                         rid += 1
    #                         eid +=1
    #
    #                 elif(has_character and (not has_cv)):
    #                     t.write("insert into `entity` VALUE (" + str(
    #                         eid) + ",1,'" + character + "','100','100','person','#3455EA');\n")
    #                     character_cv.append(character + " " + cv + " " + str(eid))
    #                     t.write("insert into `relation` VALUE (" + str(rid) + ",'出演',1," + str(
    #                         eid) + "," + str(bangumi) + ");\n")
    #                     rid += 1
    #                     t.write(
    #                         "insert into `relation` VALUE (" + str(rid) + ",'配音',1," + str(cv_dic[cv]) + "," + str(
    #                             eid) + ");\n")
    #                     rid += 1
    #                     eid += 1
    #
    #                 elif((not has_character) and (not has_cv)):
    #                     t.write("insert into `relation` VALUE (" + str(rid) + ",'出演',1," + str(
    #                         character_dic[character]) + "," + str(bangumi) + ");\n")
    #                     rid += 1
    #                     t.write("insert into `relation` VALUE (" + str(rid) + ",'配音',1," + str(cv_dic[cv]) + "," + str(
    #                         character_dic[character]) + ");\n")
    #                     rid += 1
    #                     temp = character + " " + cv + " " + str(character_dic[character])
    #                     character_cv.append(temp)
    #                 elif((not has_character) and has_cv):
    #                     t.write("insert into `relation` VALUE (" + str(rid) + ",'出演',1," + str(
    #                         character_dic[character]) + "," + str(bangumi) + ");\n")
    #                     rid += 1
    #                     t.write("insert into `relation` VALUE (" + str(rid) + ",'配音',1," + str(cv_dic[cv]) + "," + str(
    #                         character_dic[character]) + ");\n")
    #                     rid += 1
    #                     temp = character + " " + cv + " " + str(character_dic[character])
    #                     character_cv.append(temp)
    #                 else:
    #                     continue

    with open("default.dic", "w") as d:
        with open('data.csv', 'r') as f:
            reader = csv.reader(f)
            for row in reader:
                # bangumi
                bangumi = eid

                # bangumi staff
                staff_raw = row[12]
                if (len(staff_raw) != 0):
                    if (staff_raw[0] == ","): staff_raw = staff_raw[1:]
                    staff_raw = staff_raw + ','
                    staff_list = clean_array(re.findall('(.*?),', staff_raw))
                    if (len(staff_list) != 0):
                        for item in staff_list:
                            if (":" not in item):
                                continue
                            type = item[0:item.find(":")]
                            name = item[item.find(":") + 1:]
                            type = cht_to_chs(type)
                            name = cht_to_chs(name)
                            if (type == "导演" or type == "监督"):
                                director = name
                                if ('/' in director): director = director[0:director.find('/')]
                                if ('(' in director): director = director[0:director.find('(')]
                                if (not (director in director_dic.keys())):
                                    director_dic[director] = eid
                                    eid += 1
                                    d.write(director + "\t" + "nr" + "\t" + "1" + "\n")
                            if (type == "动画制作" or type == "制作"):
                                creator = name
                                if ('/' in creator): creator = creator[0:creator.find('/')]
                                if ('(' in creator): creator = creator[0:creator.find('(')]
                                if (not (creator in creator_dic.keys())):
                                    creator_dic[creator] = eid
                                    eid += 1
                                    d.write(creator + "\t" + "nz" + "\t" + "2" + "\n")