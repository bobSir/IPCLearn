package carousel.em.com.ipctest;

import android.os.RemoteException;

/**
 * Created by cly on 18/9/12.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
