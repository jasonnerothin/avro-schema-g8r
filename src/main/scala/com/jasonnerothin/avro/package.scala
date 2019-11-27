package com.jasonnerothin.avro

object Command extends Enumeration {

  protected case class Val(abbr: Char, desc: String) extends super.Val

  import scala.language.implicitConversions
  implicit def valueToControlValue(v: Value): Val = v.asInstanceOf[Val]

  val AddElement: Val = Val('A', "ADD element")
  val Undo: Val = Val('Z', "Undo last ADD")
  val Print: Val = Val('J', "Print JSON")
  val Quit: Val = Val('Q', "Quit")
  val Debug: Val = Val('D', "Print debug info")

}

object SchemaType extends Enumeration {

  protected case class Val(abbr: Char) extends super.Val

  import scala.language.implicitConversions
  implicit def valueToSchemaTypeValue(v: Value): Val = v.asInstanceOf[Val]

  val Null = Val('0')
  val Boolean = Val('B')
  val String = Val('S')
  val Numeric = Val('N')
  val Record = Val('R')
  val Enumeration = Val('E')
  val Array = Val('A')
  val Map = Val('M')
  val Union = Val('U')
  val FixedValue = Val('F')

}


