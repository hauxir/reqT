/*     
**                  _______        
**                 |__   __|   reqT - a free requriements engineering tool  
**   _ __  ___   __ _ | |      (c) 2011-2014, Lund University  
**  |  __|/ _ \ / _  || |      http://reqT.org
**  | |  |  __/| (_| || |   
**  |_|   \___| \__  ||_|   
**                 | |      
**                 |_|      
** reqT is open source, licensed under the BSD 2-clause license: 
** http://opensource.org/licenses/bsd-license.php 
***************************************************************************/

package reqT
import java.lang.{System => JSystem}

trait Init {

  def makeIMain(msg: String = repl.versionMsg): scala.tools.nsc.interpreter.IMain = {
    //to be used when no ILoop is needed, e.g. from another main
    //reqT.initInterpreter()
    println(msg)
    val settings = new scala.tools.nsc.Settings()
    settings.classpath.value = JSystem.getProperty("java.class.path")
    val writer = new java.io.PrintWriter((new java.io.OutputStreamWriter(Console.out)))
    new scala.tools.nsc.interpreter.IMain(settings, writer)
  }
  
  def initInterpreter(intp: scala.tools.nsc.interpreter.IMain = makeIMain() )  {
    println("** Initializing reqT ...")
    println("** import scala.language._")
    intp.quietRun("import scala.language._")
    println("** import reqT._")
    intp.quietRun("import reqT._")
    //intp.quietRun("import reqT." + reqt.elementNames.mkString("{",", ","}")) //to allow tab completion on model elements
    //intp.quietRun("import reqT.abbrev._")
  }
  
  def initInterpreterQuietly(intp: scala.tools.nsc.interpreter.IMain = makeIMain() )  {
    intp.quietRun("import scala.language._")
    intp.quietRun("import reqT._")
  }

  def init(intp: scala.tools.nsc.interpreter.IMain) {
    println(repl.startMsg)
    initInterpreter(intp)
  }

}