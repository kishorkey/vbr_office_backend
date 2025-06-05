package com.VbrOffice.vbr.Util;

import org.springframework.stereotype.Component;

// Method 1 - Thread Class
@Component
class ThreadImpl extends Thread
{
      // Method to start Thread
      @Override
      public void run()
    {
          String str = "Thread Class Implementation Thread"
                      + " is Running Successfully";
        System.out.println(str);
    }
}