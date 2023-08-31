from flask import *
from src.dbconnectionnew import *


app = Flask(__name__)
app.secret_key="334"
#Admin module


import functools

def login_required(func):
    @functools.wraps(func)
    def secure_function():
        if "L_id" not in session:
            return render_template('index.html')
        return func()

    return secure_function


@app.route('/logout')
def logout():
    session.clear()
    return redirect('/')



@app.route('/')
def mainpg():
    return render_template('index.html')

# @app.route('/logout')
# def logout():
#     return render_template('index.html')

@app.route('/login',methods=['post','get'])

def login():
    username=request.form['textfield']
    password=request.form['textfield2']
    qry="select * from login where username=%s and password=%s"
    val=(username,password)
    res=selectone(qry,val)
    if res is None:
        return '''<script>alert("invalid");window.location="/"</script>'''
    elif res['Type']=="admin":
        session['L_id']=res['L_id']
        return redirect('/Admin_home')
    elif res['Type']=="hospital":
        session['L_id'] = res['L_id']
        return redirect('/hospital_home')
    else:
        return '''<script>alert("invalid");window.location="/"</script>'''


@app.route('/Admin_home',methods=['post','get'])
@login_required
def admin_home():
    return render_template('admin/admin_index.html')



@app.route('/verify_hospital',methods=['post','get'])
@login_required
def verify_hospital():
    qry="SELECT hospital_reg.*,login.type FROM login JOIN hospital_reg ON hospital_reg.L_id=login.L_id WHERE login.type='pending';"
    res=selectall(qry)
    return render_template('Admin/verify_hospital.html', val=res)


#ACCEPT HOSPITAL REQUEST
@app.route('/accept_hosp',methods=['post','get'])
@login_required
def accept_hosp():
    id=request.args.get("id")
    qry="UPDATE `login` SET `Type`='hospital' WHERE `L_id`=%s"
    iud(qry,id)
    return '''<script>alert("Accepted");window.location="/verify_hospital"</script>'''


#REJECT HOSPITALREQUEST
@app.route('/reject_hosp',methods=['post','get'])
@login_required
def reject_hosp():
    id=request.args.get("id")
    qry="DELETE FROM `login` WHERE `L_id`=%s"
    iud(qry,id)
    qry1="DELETE FROM `hospital_reg` WHERE `L_id`=%s"
    iud(qry1,id)
    return '''<script>alert("REJECTED");window.location="/verify_hospital"</script>'''

#VERIFY DRIVER
@app.route('/verify_driver',methods=['post','get'])
@login_required
def verify_driver():
    qry="SELECT driver_reg.*,login.type FROM driver_reg JOIN login ON driver_reg.L_id=login.L_id WHERE login.type='pending'"
    res = selectall(qry)
    return render_template('Admin/verify_driver.html', val=res)

#ACCEPT DRIVER REQUEST
@app.route('/accept_driver',methods=['post','get'])
@login_required
def accept_driver():
    id=request.args.get("id")
    qry="UPDATE `login` SET `Type`='driver' WHERE `L_id`=%s"
    iud(qry,id)
    return '''<script>alert("Accepted");window.location="/verify_hospital"</script>'''


#REJECT DRIVER REQUEST
@app.route('/reject_driver',methods=['post','get'])
@login_required
def reject_driver():
    id=request.args.get("id")
    qry="DELETE FROM `login` WHERE `L_id`=%s"
    iud(qry,id)
    qry1="DELETE FROM `driver_reg` WHERE `L_id`=%s"
    iud(qry1,id)
    return '''<script>alert("REJECTED");window.location="/verify_hospital"</script>'''


#BLOCK DRIVER TABLE DATA
@app.route('/block_driver_data',methods=['post','get'])
@login_required
def block_driver_data():
    qry = "SELECT `driver_reg`.*,`login`.`Type` FROM `driver_reg` JOIN `login` ON `login`.`L_id`=`driver_reg`.`L_id`"
    res = selectall(qry)
    return render_template('Admin/blockdriver.html', val=res)


#BLOCK DRIVER
@app.route('/block_driver',methods=['post','get'])
@login_required
def block_driver():
    id=request.args.get('id')

    qry="UPDATE `login` SET `Type`='driver_blocked' WHERE `L_id`=%s"
    iud(qry,str(id))
    return '''<script>alert("Blocked");window.location='/block_driver_data'</script>'''


#UNBLOCK DRIVER
@app.route('/unblock_driver',methods=['post','get'])
@login_required
def unblock_driver():
    id=request.args.get('id')
    qry = "UPDATE `login` SET `Type`='driver' WHERE `L_id`=%s"
    iud(qry, id)
    return '''<script>alert("Unblocked");window.location="/block_driver_data"</script>'''


@app.route('/view_driver_rating',methods=['post','get'])
@login_required
def view_driver_rating():
    qry="SELECT driver_rating.*,driver_reg.F_name,driver_reg.L_name FROM driver_rating JOIN driver_reg ON driver_rating.D_id=driver_reg.D_id"
    res = selectall(qry)
    return render_template('Admin/view_driver_rating.html', val=res)

@app.route('/view_user',methods=['post','get'])
@login_required
def view_user():
    qry="SELECT * FROM user_reg"
    res=selectall(qry)
    return render_template('Admin/view_user.html',val=res)

#BLOCK USER DATA
@app.route('/block_user_data',methods=['post','get'])
@login_required
def block_user_data():
    qry = "SELECT `user_reg`.*,`login`.`Type` FROM `user_reg` JOIN `login` ON `login`.`L_id`=`user_reg`.`L_id`"
    res = selectall(qry)
    return render_template('Admin/block_user.html' , val=res)

#BLOCK USER
@app.route('/block_user',methods=['post','get'])
@login_required
def block_user():
    id=request.args.get('id')

    qry="UPDATE `login` SET `Type`='user_blocked' WHERE `L_id`=%s"
    iud(qry,str(id))
    return '''<script>alert("Blocked");window.location='/block_user_data'</script>'''


#UNBLOCK USER
@app.route('/unblock_user',methods=['post','get'])
@login_required
def unblock_user():
    id=request.args.get('id')
    qry = "UPDATE `login` SET `Type`='user' WHERE `L_id`=%s"
    iud(qry, id)
    return '''<script>alert("Unblocked");window.location="/block_user_data"</script>'''


@app.route('/view_feedback',methods=['post','get'])
@login_required
def view_feedback():
    qry="SELECT feedback.*,driver_reg.F_name,driver_reg.L_name,user_reg.F_name as uname,user_reg.L_name as unamee FROM feedback JOIN driver_reg JOIN user_reg ON feedback.D_id=driver_reg.D_id AND feedback.U_id=user_reg.U_id"
    res=selectall(qry)
    return render_template('Admin/view_feedback.html',val=res)

@app.route('/view_feedback',methods=['post','get'])
@login_required
def View_feedback():
    return render_template('Admin/view_feedback.html')

#Hospital module

@app.route('/signup',methods=['post','get'])
def signup():
    return render_template('Hospital/signup_index.html')

@app.route('/signup1',methods=['post','get'])
def signup1():
    hos_name = request.form['textfield']
    state=request.form['country']
    place = request.form['textfield1']
    pin = request.form['textfield2']
    post = request.form['textfield3']
    phone = request.form['textfield4']
    email = request.form['textfield5']
    Latitude=request.form['textfield6']
    Longitude = request.form['textfield7']
    username = request.form['textfield8']
    password = request.form['textfield9']
    qry = "insert into login values (null,%s,%s,'pending')"
    val = (username, password)
    res = iud(qry, val)
    qry2 = "insert into hospital_reg values (null,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    val2 = (res, hos_name, state,place, pin, post, phone, email,Latitude,Longitude)
    res = iud(qry2,val2)
    return '''<script>alert("Successfully Registered");window.location="/"</script>'''

@app.route('/hospital_home',methods=['post','get'])
@login_required
def hospital_home():
    return render_template('Hospital/hospital_index.html')


@app.route('/view_notification_driver',methods=['post','get'])
@login_required
def view_notification_driver():
    lid=session['L_id']
    qry="SELECT `hospital_req`.* ,`driver_reg`.*,`ambulance_location`.* FROM `hospital_req` JOIN `driver_reg` ON `driver_reg`.`L_id`=`hospital_req`.`L_id` JOIN `ambulance_location` ON `driver_reg`.`L_id`=`ambulance_location`.`D_id` WHERE `hospital_req`.`H_id`=%s "
    res=selectall2(qry,lid)
    print(res)
    return render_template('Hospital/view_notif_from_ambulance_driver.html',val=res)

@app.route('/track_driver_location',methods=['post','get'])
@login_required
def track_driver_location():
    return render_template('Hospital/track_ambulance_loc.html')


@app.route('/index', methods=['POST'])
def index():



    data=[]
    qry="SELECT `R_id` FROM `hospital_req` WHERE `R_id` NOT IN(SELECT `noti_id` FROM `hospital_noti`) AND `H_id`=%s"
    res=selectall2(qry,session['L_id'])
    status="na"
    if len(res)>0:
        status="yes"
        for i in res:
            iud("insert into hospital_noti values(%s)",i['R_id'])
    print (status)
    resp = make_response(jsonify(status))
    resp.status_code = 200
    resp.headers['Access-Control-Allow-Origin'] = '*'
    return resp

app.run(debug=True)
