package com.jasonnerothin.avro

import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.StdIn

/**
 * Generator (c) by jasonnerothin@gmail.com
 *
 * Generator is licensed under the
 * Apache v2 license
 *
 * You should have received a copy of the license along with this
 *   work.  If not, see <https://www.apache.org/licenses/LICENSE-2.0>.
 */
object Generator {

  def commandMenu(): Unit = {
    val builder = new StringBuilder
    builder.append("\n>> WHAT NEXT? <<")
    for (c <- Command.values) builder.append(s"\n${c.abbr}:\t${c.desc}")
    printf(builder.append("\n> ").toString)
  }

  def elementMenu(): Unit = {
    val builder = new StringBuilder("\n>> ADD ELEMENT <<")
    for (st <- SchemaType.values) builder.append(s"\n${st.abbr}:\t${st}")
    printf(builder.append("\n>").toString)
  }

  private var schemaElements = mutable.Stack[SchemaType.Value]()

  def main(args: Array[String]): Unit = {
    while (process(nextCommand)) {}
  }

  private def process(command: Command.Value): Boolean = {
    command match {
      case Command.Print =>
        printf("TODO: print\n")
        true
      case Command.AddElement =>
        val e = whichSchemaElement
        printf(s"[INFO] Creating one $e...\n")
        schemaElements.push(e)
        true
      case Command.Undo =>
        if (schemaElements.nonEmpty) {
          val popped = schemaElements.pop()
          printf(s"[INFO] Undoing one $popped.\n")
        }
        else printf("[ERROR] Nothing to undo.\n")
        true
      case Command.Debug =>
        val builder = new StringBuilder("[DEBUG] SCHEMA:\n")
        var i = 0
        for (se <- schemaElements.reverse) {
          builder.append(s"[DEBUG] [$i] $se\n")
          i = i + 1
        }
        if( schemaElements.isEmpty ) builder.append("NONE\n")
        printf(builder.toString)
        true
      case Command.Quit =>
        printf("[INFO] Thanks for stopping in. Goodbye!\n")
        false
    }
  }

  def whichSchemaElement: SchemaType.Value = {
    val result = askUser[SchemaType.Value](
      elementMenu,
      str => isValidLength(str) && SchemaType.values.exists(_.abbr == upperChar(str)),
      ch => SchemaType.values.find(_.abbr == ch).get
    )
    result
  }

  def nextCommand: Command.Value = {
    val result = askUser[Command.Value](commandMenu,
      str => isValidLength(str) && Command.values.exists(_.abbr == upperChar(str)),
      ch => Command.values.find(_.abbr == ch).get
    )
    //    printf(s"Very well, I will ${result.desc}...\n")
    result
  }

  @tailrec
  def askUser[T](printMenu: () => Unit, validResponse: String => Boolean, map: Char => T): T = {
    printMenu.apply()
    val response = StdIn.readLine.trim()
    if (!isValidLength(response) || !validResponse(response)) {
      printf(s"[ERROR] Invalid input: '$response'\n")
      askUser(printMenu, validResponse, map)
    } else {
      map(upperChar(response))
    }
  }

  private def isValidLength(str: String): Boolean = str.trim.length == 1

  private def upperChar(str: String): Char = {
    require(str.length > 0, "input should have length > 0")
    str.trim.toCharArray.head.toUpper
  }

}