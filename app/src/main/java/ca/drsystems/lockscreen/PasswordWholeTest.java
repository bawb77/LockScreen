package ca.drsystems.lockscreen;


public class PasswordWholeTest {
    public void main(String args[]) {
        Test();
    }
    public void Test(){
        PasswordBlock passwordBlockTest;
        PasswordBlock[] passwordBlocks = new PasswordBlock[3];
        for(int i = 0; i<3;i++){

            passwordBlocks[i] = new PasswordBlock(0,1,2);
        }
    PasswordWhole testCase = new PasswordWhole(passwordBlocks,true,true,true);
       if(testCase.checkIt(passwordBlocks)){
           System.out.print("Success");
       }
       else{
           System.out.print("Failure");
       }
    PasswordWhole testCase2 = new PasswordWhole(passwordBlocks,false,false,false);
    if(!testCase.checkIt(passwordBlocks)){
        System.out.print("Success");
    }
    else{
        System.out.print("Failure");
    }
        PasswordWhole testCase3 = new PasswordWhole(passwordBlocks,false,false,true);
        if(testCase.checkIt(passwordBlocks)){
            System.out.print("Success");
        }
        else{
            System.out.print("Failure");
        }
    }

}
