package net.tclproject.biomeidextender.asm;

public class AsmTransformException extends RuntimeException {
   private static final long serialVersionUID = 5128914670008752449L;

   public AsmTransformException(String message) {
      super(message);
   }

   public AsmTransformException(Throwable cause) {
      super(cause);
   }

   public AsmTransformException(String message, Throwable cause) {
      super(message, cause);
   }
}
