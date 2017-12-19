/*
  * 创建用户登陆的对话框
  * 登陆界面包含两个按钮
  * 1按钮为登陆
  * 2按钮为不登陆试玩
  * */
private void CreateLoginAlert()
        {
        AlertDialog.Builder ad =new AlertDialog.Builder(this);
        ad.setTitle("账号登陆");
        ad.setView(ViewUtility.GetView(this,R.layout.sub_logindialog));
        adi= ad.create();
     
   
  /*  
    */
        adi.setButton("登陆", new OnClickListener(){
@Override
public void onClick(DialogInterface arg0, int arg1) {

        EditText password=  (EditText)adi.findViewById(R.id.txt_password);
        EditText account =(EditText)adi.findViewById(R.id.txt_username);

        PassWord=password.getText().toString();
        Account=account.getText().toString();
        //生成登陆对话框
        m_Dialog=ProgressDialog.show(Main.this, "请等待...", "正在为你登陆...",true);
        mRedrawHandler.sleep(100);
        }
        });

        adi.setButton2("试 玩", new OnClickListener(){
@Override
public void onClick(DialogInterface arg0, int arg1) {
        ViewUtility.NavigateActivate(Main.this, SelectTheme.class);
        }
        });

        adi.show();


        //设置注册点击事件
        TextView register=(TextView)adi.findViewById(R.id.txt_toregister);
        register.setOnClickListener(new TextView.OnClickListener()
        {
public void onClick(View v){
        //创建注册对话框
        CreateRegisterAlert();
        adi.dismiss();

        }
        });

        }
   
  /*
  *定时线程做验证用
  * */
private RefreshHandler mRedrawHandler = new RefreshHandler();

class RefreshHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {

        try{

            //调用网络接口，实现登陆指令
            Boolean flags=  UserDataServiceHelper.Login(Account, PassWord);
            if(flags)
            {
                //保存登陆信息
                UserDataWriteHelper uw=new UserDataWriteHelper(Main.this);
                uw.SaveUserInfoInDB("xuwenbing", Account);
                //提示登陆成功
                Toast.makeText(Main.this, "登陆成功", Toast.LENGTH_SHORT).show();
                //转到主题页面
                ViewUtility.NavigateActivate(Main.this, SelectTheme.class);
            }else
            {
                //失败 显示错误信息
                Toast.makeText(Main.this, "登陆失败", Toast.LENGTH_SHORT).show();
                adi.show();
                adi.findViewById(R.id.txt_loginerror).setVisibility(View.VISIBLE);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            m_Dialog.dismiss();
        }
    }
    public void sleep(long delayMillis) {
        this.removeMessages(0);
        sendMessageDelayed(obtainMessage(0), delayMillis);
    }
};