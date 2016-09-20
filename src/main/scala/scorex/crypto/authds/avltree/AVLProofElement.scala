package scorex.crypto.authds.avltree

import scorex.crypto.encode.Base58

sealed trait AVLProofElement {
  val bytes: Array[Byte]
}

case class AVLProofLevel(e: Level) extends AVLProofElement {
  val bytes: Array[Byte] = e.bytes
}

trait AVLProofLabel extends AVLProofElement {
  val e: Array[Byte]
  val bytes: Array[Byte] = e

  override def toString: String = s"WTProofKey(${Base58.encode(e).take(8)})"
}

case class AVLProofRightLabel(e: Array[Byte]) extends AVLProofLabel

case class AVLProofLeftLabel(e: Array[Byte]) extends AVLProofLabel

trait Key extends AVLProofElement {
  val e: Array[Byte]
  val bytes: Array[Byte] = e

  override def toString: String = s"Key(${Base58.encode(e).take(8)})"
}

case class AVLProofKey(e: WTKey) extends Key
case class AVLProofNextLeafKey(e: WTKey) extends Key

case class AVLProofValue(e: WTValue) extends AVLProofElement {
  val bytes: Array[Byte] = e

  override def toString: String = s"WTProofKey(${Base58.encode(e).take(8)})"
}

case class AVLProofDirection(direction: Direction) extends AVLProofElement {
  override val bytes: Array[Byte] = Array(direction match {
    case LeafFound => 1: Byte
    case LeafNotFound => 2: Byte
    case GoingLeft => 3: Byte
    case GoingRight => 4: Byte
  })
}

sealed trait Direction

case object LeafFound extends Direction {}

case object LeafNotFound extends Direction

case object GoingLeft extends Direction

case object GoingRight extends Direction
