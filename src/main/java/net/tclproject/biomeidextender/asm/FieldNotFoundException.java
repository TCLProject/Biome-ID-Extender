package net.tclproject.biomeidextender.asm;

public class FieldNotFoundException extends AsmTransformException {
   public FieldNotFoundException(String field) {
      super("can't find field " + field);
   }
}
