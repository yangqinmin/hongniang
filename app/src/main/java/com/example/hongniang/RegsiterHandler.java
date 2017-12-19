/*创建注册对话框*/
private void CreateRegisterAlert()
        {
        //registerdialog
        AlertDialog.Builder ad =new AlertDialog.Builder(this);
        ad.setTitle("注册账号");
        ad.setView(ViewUtility.GetView(this,R.layout.sub_registerdialog));
        registerdialog= ad.create();

        registerdialog.setButton("注册", new OnClickListener(){
@Override
public void onClick(DialogInterface arg0, int arg1) {

        EditText password=  (EditText)registerdialog.findViewById(R.id.txt_password);
        EditText account =(EditText)registerdialog.findViewById(R.id.txt_username);
        EditText nicename =(EditText)registerdialog.findViewById(R.id.txt_nicename);

        PassWord=password.getText().toString();
        Account=account.getText().toString();
        NiceName=nicename.getText().toString();
        //生成注册对话框
        m_Dialog=ProgressDialog.show(Main.this, "请等待...", "正在为你注册...",true);
        mRegsiterHandler.sleep(100);
        }
        });

        registerdialog.setButton2("试 玩", new OnClickListener(){
@Override
public void onClick(DialogInterface arg0, int arg1) {
        ViewUtility.NavigateActivate(Main.this, SelectTheme.class);
        }
        });

        registerdialog.show();
        }
  /*
  *定时注册程序
  * */
private RegsiterHandler mRegsiterHandler = new RegsiterHandler();

class RegsiterHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {

        try{

            //调用网络接口，实现注册指令
            Boolean flags=  UserDataServiceHelper.Register(Account, PassWord,NiceName);
            if(flags)
            {
                //保存注册信息
                UserDataWriteHelper uw=new UserDataWriteHelper(Main.this);
                uw.SaveUserInfoInDB("xuwenbing", Account);
                //提示注册成功
                Toast.makeText(Main.this, "注册成功", Toast.LENGTH_SHORT).show();
                //转到主题页面
                ViewUtility.NavigateActivate(Main.this, SelectTheme.class);
            }else
            {
                //失败 显示错误信息
                Toast.makeText(Main.this, "注册失败", Toast.LENGTH_SHORT).show();
                registerdialog.show();
                registerdialog.findViewById(R.id.txt_loginerror).setVisibility(View.VISIBLE);
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