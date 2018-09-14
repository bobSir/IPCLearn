package carousel.em.com.ipctest;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

public class BindPollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_poll);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securtiBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        IsecurityCenter mSecurityCenter = SecurityCenterImpl.asInterface(securtiBinder);
        String msg = "hello world";
        try {
            String passWord = mSecurityCenter.encrypt(msg);
            System.out.println("@cly" + "--encrypt:" + passWord +
                    "--decrypt:" + mSecurityCenter.decrypt(passWord));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute compute = ComputeImpl.asInterface(computeBinder);
        try {
            int result = compute.add(3, 5);
            System.out.println("@cly" + "3+5 = " + result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
