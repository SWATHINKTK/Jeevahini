from flask import *
from src.dbconnectionnew import *


app = Flask(__name__)

#SIGNUP dr
@app.route('/signup1',methods=['post'])
def signup1():
    fname= request.form['fname']
    lname= request.form['lname']
    address = request.form['address']
    Vehicle_no = request.form['Vehicle_no']
    License_no = request.form['License_no']
    Phone_no=request.form['Phone_No']
    Email= request.form['Email']
    username=request.form['uname']
    password = request.form['passwd']
    qry="insert into login values (null,%s,%s,'pending')"
    val= (username, password)
    res = iud(qry, val)
    qry1="insert into driver_reg values(null,%s,%s,%s,%s,%s,%s,%s,%s,'pending')"
    val2 = (res, fname,lname, address,Vehicle_no, License_no, Phone_no, Email)
    iud(qry1, val2)
    print(val2)
    return jsonify({"task": "valid"})



#USER LOGIN
@app.route('/login',methods=['POST'])
def login():

    username=request.form['uname']
    password=request.form['password']

    qry="select * from login where username=%s and password=%s"
    val=(username,password)
    res=selectone(qry,val)
    print(res)

    if res is None:
        return jsonify({'task':'invalid'})
    elif res['Type']=="driver":
        type = res['Type']
        id = res['L_id']

        qry = "SELECT `driver_reg`.`F_name`,`L_name` FROM `driver_reg` WHERE `L_id`=%s"
        res1 = selectone(qry, id)

        return jsonify({'task': 'success', 'lid': id, 'type': type, 'name': res1['F_name'] + res1['L_name']})
    elif res['Type']=="user":
        type= res['Type']
        id=res['L_id']

        qry = "SELECT `user_reg`.`F_name`,`L_name` FROM `user_reg` WHERE `L_id`=%s"
        res1 = selectone(qry, id)

        return jsonify({'task':'success','lid':id ,'type':type,'name':res1['F_name']+res1['L_name']})

    else:
        return jsonify({'task': 'invalid'})

#TRACKDRIVER PENDING



#SEND REQUEST
@app.route('/send_request',methods=['post'])
def send_request():
    print(request.form)
    did=request.form['driver_id']
    lid=request.form['lid']
    lati = request.form['lati']
    longi = request.form['longi']

    qry = "SELECT * FROM `request` WHERE `D_id`=%s AND `L_id`=%s AND `Request_status`='pending'"
    res = selectone(qry,(did,lid))

    if res is None:
        qry="INSERT INTO `request` VALUES (NULL,%s,%s,'pending',%s,%s)"
        val=(did,lid,lati,longi)
        iud(qry,val)
        return jsonify({"task":"success"})
    else:
        return jsonify({"task":"failed"})


@app.route('/view_req_status',methods=['post'])
def view_req_status():
    lid=request.form['lid']
    q="SELECT`request`.*,`driver_reg`.*,`ambulance_location`.* FROM `request` JOIN `driver_reg` ON `request`.`D_id`=`driver_reg`.`L_id` JOIN`ambulance_location` ON `ambulance_location`.`D_id`=`request`.D_id  WHERE  `request`.`L_id`=%s and `request`.`Request_status` !='paid'"
    res=selectall2(q,lid)
    print(res)
    return jsonify(res)


@app.route('/payment_details', methods=['post'])
def payment_details():
    lid = request.form['lid']
    qry = "SELECT `user_reg`.`F_name`,`L_name`,`payment`.`Amount`,`Date` FROM `payment` JOIN `request` ON `payment`.`req_id`=`request`.`Req_id` JOIN `user_reg` ON `request`.`L_id`=`user_reg`.`L_id` WHERE `request`.`D_id`=%s"
    res = selectall2(qry, lid)

    return jsonify(res)


#Driver selection
@app.route('/view_driver', methods=['POST'])
def view_driver():
    id = request.form['lid']
    qry="SELECT `driver_reg`.`F_name`,`L_name`,`driver_reg`.`L_id` FROM `driver_reg` JOIN `request` ON `driver_reg`.`L_id`=`request`.`D_id` WHERE `request`.`L_id`=%s AND `request`.`Request_status`='Completed'"
    res=androidselectall(qry,id)
    print(res)
    return jsonify(res)


#NOTIFY HOSPITAL  **CHECK**
@app.route('/notify_hospital',methods=['POST'])
def notify_hospital():
    print(request.form)
    latitude = request.form['lati']
    longitude = request.form['longi']
    print(latitude)
    print(longitude)
    q = "SELECT `hospital_reg`.* ,(3959 * ACOS ( COS ( RADIANS('" + latitude + "') ) * COS( RADIANS(latitude) ) * COS( RADIANS( longitude ) - RADIANS('" + longitude + "') ) + SIN ( RADIANS('" + latitude + "') ) * SIN( RADIANS( latitude ) ))) AS user_distance FROM `hospital_reg`   HAVING user_distance  < 31.068"
    s = selectall(q)
    print('location', s)
    return jsonify(s)


#SEND FEEDBACK
@app.route('/send_feedback',methods=['POST'])
def send_feedback():
    driver_id = request.form['driver']
    user_id = request.form['user']
    feedback = request.form['feedback']
    rate = request.form['Rating']
    qry="insert into  feedback values (null,%s,%s,%s,curdate())"
    val = (driver_id,user_id, feedback)
    res = iud(qry, val)
    qry = "insert into `driver_rating` values (null,%s,%s,%s,curdate())"
    val1 = (driver_id, user_id, rate)
    res = iud(qry, val1)
    return jsonify({"task": "valid"})

#RATE DRIVER
@app.route('/rate_driver',methods=['POST'])
def rate_driver():
    driver_id = request.form['driver']
    user_id = request.form['user']
    rate = request.form['Rating']
    qry="inser into  driver_rate values (null,%s,%s,%s)"
    val = (driver_id,user_id, rate)
    res = iud(qry, val)
    return jsonify(res)


#DRIVER MODULE

#SIGNUP
@app.route('/signup',methods=['post'])
def signup():
    fname= request.form['fname']
    lname= request.form['lname']
    address= request.form['address']

    Phone_no=request.form['Phone_no']
    Email= request.form['Email']
    username=request.form['uname']
    password = request.form['passwd']
    qry="insert into login values (null,%s,%s,'pending')"
    val= (username, password)
    res = iud(qry, val)
    qry1="insert into user_reg  values (null,%s,%s,%s,%s,%s,%s)"
    val2 = (res, fname,lname, address,Phone_no,Email)
    iud(qry1, val2)
    print(val2)
    return jsonify({"task": "valid"})

#DRIVER LOGIN
# @app.route('/login',methods=['POST'])
# def login():
#
#     username=request.form['uname']
#     password=request.form['password']
#
#     qry="select * from login where username=%s and password=%s and type='driver'"
#     val=(username,password)
#     res=selectone(qry,val)
#
#     if res is None:
#         return jsonify({'task':'invalid'})
#
#     else:
#         id= res['type']
#         lid=res['L_id']
#         return jsonify({'task':'success','lid':id ,'type':type})

#UPDATE STATUS **CHECK**
@app.route('/update_status',methods=['POST'])
def update_status():
    driver_id = request.form['driver']
    Status = request.form['Status']
    print(Status)
    print(driver_id)
    qry="UPDATE `driver_reg` SET `State`=%s WHERE L_id=%s"
    val = (Status,driver_id)
    res = iud(qry, val)
    return jsonify({"task":"valid"})

#VEW OR ACCEPT REQUEST
@app.route('/viewemergencyreq',methods=['POST'])
def viewemergencyreq():
    login_id = request.form['lid']
    qry="SELECT `request`.*,`user_reg`.* FROM `request` JOIN `user_reg` ON `user_reg`.`L_id`=`request`.`L_id` WHERE `request`.`D_id`=%s "
    val = (login_id)
    res = selectall2(qry,val)
    print(res)
    return jsonify(res)

#ACCEPT REQUEST
@app.route('/acceptreq',methods=['POST'])
def acceptreq():
    Req_id = request.form['rid']
    qry="UPDATE `request` SET `Request_status`= 'Accepted' WHERE `Req_id`=%s "
    val = (Req_id)
    res = iud(qry,val)
    print(res)
    return jsonify({"task": "valid"})

#REJECT REQUEST
@app.route('/rejectreq',methods=['POST'])
def rejectreq():
    Req_id = request.form['rid']
    qry="UPDATE `request` SET `Request_status`= 'Rejected' WHERE `Req_id`=%s "
    val = (Req_id)
    res = iud(qry,val)
    print(res)
    return jsonify({"task": "valid"})




@app.route('/completedreq',methods=['POST'])
def completedreq():
    Req_id = request.form['rid']
    qry="UPDATE `request` SET `Request_status`= 'Completed' WHERE `Req_id`=%s "
    val = (Req_id)
    res = iud(qry,val)
    print(res)
    return jsonify({"task": "valid"})


#paid
@app.route('/paid', methods=['POST'])
def paid():
    amount = request.form['amt']
    rid = request.form['rid']
    qry = "INSERT INTO `payment` VALUES(NULL,%s,%s,CURDATE())"
    iud(qry,(rid,amount))

    qry = "UPDATE `request` SET `Request_status`='paid' WHERE `Req_id`=%s"
    iud(qry,rid)

    return jsonify({"task": "valid"})

#
# #NOTIFY HOSPITAL
# @app.route('/notify',methods=['POST'])
# def notify():
#     qry="SELECT * FROM `hospital_reg` "
#     res=androidselectallnew(qry)
#     return jsonify(res)

#TRACK LOCATION PENDING

#REPORT USER
@app.route('/report',methods=['POST'])
def report():
    driver_id=request.form['driver']
    user_id = request.form['user']
    report = request.form['reason']
    qry="INSERT INTO `report` VALUES(null,%s,%s,%s,curdate()) "
    val = (driver_id, user_id, report)
    print(val)
    iud(qry,val)
    return jsonify({"task": "valid"})


@app.route('/report_user_view',methods=['POST'])
def report_user_view():
    print(request.form)
    driver_id=request.form['lid']
    qry="SELECT `user_reg`.`F_name`,`L_name`,`user_reg`.L_id FROM `request` JOIN `user_reg` ON `request`.`L_id`=`user_reg`.`L_id` WHERE `request`.`D_id`=%s AND `request`.`Request_status`='Accepted'"
    res=selectall2(qry,driver_id)
    print(res)
    return jsonify(res)


#accident level
@app.route('/accident_level',methods=['POST'])
def accident_level():
    hospital_id=request.form['hospital']
    driver_id = request.form['driver']
    level = request.form['level']
    qry="INSERT INTO `hospital_req` VALUES(null,%s,%s,%s,curdate()) "
    val = ( driver_id,hospital_id, level)
    print(val)
    iud(qry,val)
    return jsonify({"task": "valid"})


#=============nearamb==========


@app.route('/user_view_ambulance',methods=['POST'])
def user_view_ambulance():
    print(request.form)
    latitude=request.form['lati']
    longitude=request.form['longi']
    print(latitude)
    q="SELECT `driver_reg`.* ,`ambulance_location`.*,(3959 * ACOS ( COS ( RADIANS('"+latitude+"') ) * COS( RADIANS(latitude) ) * COS( RADIANS( longitude ) - RADIANS('"+longitude+"') ) + SIN ( RADIANS('"+latitude+"') ) * SIN( RADIANS( latitude ) ))) AS user_distance FROM `driver_reg` JOIN `ambulance_location` ON `driver_reg`.`L_id`=`ambulance_location`.`D_id` WHERE `State`='Available' HAVING user_distance  < 31.068"
    s=selectall(q)
    print('location',s)
    return jsonify(s)

@app.route('/addlocation', methods=['POST'])
def addlocation():
    print(request.form)
    lid=request.form['uid']
    lati=request.form['lati']
    longi=request.form['longi']
    q="SELECT * FROM `ambulance_location` WHERE `D_id`=%s"
    res=selectall2(q,lid)

    if len(res) <=0:
        qry="INSERT INTO `ambulance_location` (`D_id`,`latitude`,`Longitude`) VALUES (%s,%s,%s)"
        val=(lid,lati,longi)
        iud(qry,val)
        return jsonify({"task":"inserted"})
    else:
        qry="UPDATE `ambulance_location` SET `latitude`=%s ,`Longitude`=%s WHERE `D_id`=%s"
        val=(lati,longi,lid)
        iud(qry,val)
        return jsonify({"task": "updated"})

##SELECT `request`.`L_id`,`request`.`Request`,`request`.`latitude`,`request`.`longitude`,`user_reg`.`L_id`,`user_reg`.`F_name`,`user_reg`.`L_name`,`user_reg`.`Address`,`user_reg`.`Phone_NO` FROM `request`  JOIN `user_reg` ON `request`.%s=`user_reg`.%s







@app.route('/acceptnotification',methods=['post'])
def acceptnotification():
    lid=request.form['lid']
    q="SELECT * FROM `request` WHERE `D_id`=%s AND `Request_status`='accepted' OR `Request_status`='rejected'"
    res = selectall2(q,lid)
    print(res)

    if len(res)==0:
        return jsonify({"task":"no"})
    else:
        return jsonify({"task":"yes"})




# =++++++++++
@app.route('/service',methods=['post'])
def service():
    print(request.form)
    lid=request.form['lid']
    qry="SELECT * FROM `request` WHERE `D_id`=%s AND `Request_status`='pending'"
    res=selectall2(qry,lid)
    if len(res)==0:
        return jsonify(task="no")
    else:
        return jsonify(task="yes")


@app.route('/user_noti_check', methods=['post'])
def user_noti_check():
    lid = request.form['lid']
    print(lid)
    qry ="SELECT * FROM `request` WHERE `Request_status`='Accepted' AND `L_id`=%s"
    res = selectone(qry,lid)
    print (res,"===========++++++++++++")
    if res is not None:
        return jsonify({"task":"yes"})
    else:
        return jsonify({"task":"no"})




app.run(host='0.0.0.0',port='5000')