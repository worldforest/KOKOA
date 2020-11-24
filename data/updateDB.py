import pymysql
import csv

#DB Select
def select_test(id):
    conn = pymysql.connect(host='k3b104.p.ssafy.io', user='ssafy', password='Tkvl!akwlakr!vmfhwprxm!', db='kokoa')
    try:
        with conn.cursor() as curs:
            sql = "select * from video where id=%s"
            curs.execute(sql, id)
            rs = curs.fetchall()
            for row in rs:
                print(row)
    finally:
        conn.close()

#DB Update
def updatedata():
    conn = pymysql.connect(host='k3b104.p.ssafy.io', user='ssafy', password='Tkvl!akwlakr!vmfhwprxm!', db='kokoa')
    f = open('video.csv', 'r')
    rdr = csv.DictReader(f)
    # for row in rdr:
    #     print(row['id'], row['title'], row['url'])

    try:
        with conn.cursor() as curs:
            for row in rdr:
                # if int(row['id']) < 11:
                #     continue
                # if row['id'] == "11":
                #     break
                sql = "UPDATE `video` SET `title` = (%s) WHERE `id` = (%s)"
                answer = [row['title'], row['id']]
                curs.execute(sql, answer)
                conn.commit()
                select_test(row['id'])
    finally:
        conn.close()

# insert
def insertdata():
    conn = pymysql.connect(host='k3b104.p.ssafy.io', user='ssafy', password='Tkvl!akwlakr!vmfhwprxm!', db='kokoa')
    f = open('video.csv', 'r')
    rdr = csv.DictReader(f)
    # for row in rdr:
    #     print(row['id'], row['title'], row['url'])

    try:
        with conn.cursor() as curs:
            for row in rdr:
                if row['memberid'] == "":
                    continue
                # if row['id'] != "5":
                #     continue
                sql = "INSERT INTO `video_member` (videoid, memberid) VALUES ((%s), (%s))"
                answer = [row['id'], row['memberid']]
                curs.execute(sql, answer)
                conn.commit()
                select_test(row['id'])
    finally:
        conn.close()


if __name__ == "__main__":
    updatedata()
    # insertdata()