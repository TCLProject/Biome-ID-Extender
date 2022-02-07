package net.tclproject.biomeidextender.asm;

public class MethodNotFoundException extends AsmTransformException {
   public MethodNotFoundException(String method) {
      super("can't find method " + method);
   }

   public MethodNotFoundException(String method, String desc) {
      super("can't find method " + method + " " + desc);
   }
}
