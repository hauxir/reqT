//load me in the scala REPL with :load reqT-init.scala
println("Welcome to reqT version " + reqt.VERSION)
if ($intp.quietRun("reqt.Model.interpreter = Some($intp)") != scala.tools.nsc.interpreter.IR.Success) 
  println("Init reqt.Model.interpreter failed.")
{
  $intp.interpret("import scala.language._")
  $intp.interpret("import reqt._")
  $intp.interpret("import reqt.abbrev._")
  ()
}