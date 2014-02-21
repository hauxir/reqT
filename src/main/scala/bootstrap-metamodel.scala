/*** 
**                  _______        
**                 |__   __|   reqT - a requriements engineering tool  
**   _ __  ___   __ _ | |      (c) 2011-2014, Lund University  
**  |  __|/ _ \ / _  || |      http://reqT.org
**  | |  |  __/| (_| || |   
**  |_|   \___| \__  ||_|   
**                 | |      
**                 |_|      
** reqT is open source, licensed under the BSD 2-clause license: 
** http://opensource.org/licenses/bsd-license.php 
**************************************************************************/
/* This is the bootstrap metamodel used when no generated metamodel exisist
//de-comment this file and remove the file "GENERATED-metamodel.scala"
//generate new metamodel with 
//reqT> reqT.metaprog.makeMetamodel().save("GENERATED-metamodel.scala")

package reqT

object metamodel extends MetamodelTypes {
  override lazy val types: Vector[MetaType] = entityTypes ++ attributeTypes ++ relationTypes
  lazy val entityTypes: Vector[EntityType] = Vector(Ent) ++ generalEntities ++ contextEntities ++ requirementEntities
  
  lazy val generalEntities: Vector[EntityType] = Vector(Section, Item, Label) 
  lazy val contextEntities: Vector[EntityType] = Vector(Stakeholder, Product, System, Subdomain)   
  lazy val requirementEntities: Vector[EntityType] = generalReqs ++ intentionalReqs
  lazy val generalReqs: Vector[EntityType] = Vector(Req, Idea, Feature)
  lazy val intentionalReqs: Vector[EntityType] = Vector(Goal, Wish)

  lazy val attributeTypes: Vector[AttributeType[_]] = stringAttributes ++ intAttributes ++ cardinalityAttributes
  lazy val stringAttributes: Vector[StringType] = Vector(Val) ++ Vector(Gist, Spec, Text, Title)
  lazy val intAttributes: Vector[IntType] = Vector(Prio, Cost)
  lazy val cardinalityAttributes: Vector[CardinalityType] = Vector(Opt)
  lazy val relationTypes: Vector[RelationType] = Vector(has, is, requires, relatesTo)
}

//Enum traits

trait Cardinality extends Enum[Cardinality] { val enumCompanion = Cardinality }
trait CardinalityCompanion extends EnumCompanion[Cardinality] { 
  val values = Vector(NoOption, Zero, One, ZeroOrOne, OneOrMany, ZeroOrMany)
  val default = NoOption
}
trait CardinalityAttribute extends Attribute[Cardinality]
trait CardinalityType extends AttributeType[Cardinality] {
  val default = Cardinality.default
  override  def apply(value: Cardinality): CardinalityAttribute
}
case object Cardinality extends CardinalityCompanion
case object NoOption extends Cardinality
case object Zero extends Cardinality
case object One extends Cardinality
case object ZeroOrOne extends Cardinality
case object OneOrMany extends Cardinality
case object ZeroOrMany extends Cardinality

   
//Concrete attributes
case class Gist(value: String) extends StringAttribute { override val myType = Gist }
case object Gist extends StringType 

case class Spec(value: String) extends StringAttribute { override val myType = Spec }
case object Spec extends StringType 

case class Text(value: String) extends StringAttribute { override val myType = Text }
case object Text extends StringType 

case class Title(value: String) extends StringAttribute { override val myType = Title }
case object Title extends StringType 

case class Prio(value: Int) extends IntAttribute { override val myType = Prio }
case object Prio extends IntType 

case class Cost(value: Int) extends IntAttribute { override val myType = Cost }
case object Cost extends IntType 

case class Opt(value: Cardinality) extends CardinalityAttribute { override val myType = Opt }
case object Opt extends CardinalityType 

//Abstract requirement traits
trait GeneralReq extends Requirement
case object GeneralReq extends AbstractSelector { type AbstractType = GeneralReq } 

trait IntentionalReq extends Requirement
case object IntentionalReq extends AbstractSelector { type AbstractType = IntentionalReq } 

//Concrete entities
case class Section(id: String) extends General { override val myType: EntityType = Section }
case object Section extends EntityType

case class Item(id: String) extends General { override val myType: EntityType = Item }
case object Item extends EntityType

case class Label(id: String) extends General { override val myType: EntityType = Label }
case object Label extends EntityType

case class Stakeholder(id: String) extends Context { override val myType: EntityType = Stakeholder }
case object Stakeholder extends EntityType

case class Product(id: String) extends Context { override val myType: EntityType = Product }
case object Product extends EntityType

case class System(id: String) extends Context { override val myType: EntityType = System }
case object System extends EntityType

case class Subdomain(id: String) extends Context { override val myType: EntityType = Subdomain }
case object Subdomain extends EntityType

case class Req(id: String) extends GeneralReq { override val myType: EntityType = Req }
case object Req extends EntityType

case class Idea(id: String) extends GeneralReq { override val myType: EntityType = Idea }
case object Idea extends EntityType

case class Feature(id: String) extends GeneralReq { override val myType: EntityType = Feature }
case object Feature extends EntityType

case class Goal(id: String) extends IntentionalReq { override val myType: EntityType = Goal }
case object Goal extends EntityType

case class Wish(id: String) extends IntentionalReq { override val myType: EntityType = Wish }
case object Wish extends EntityType

//Concrete relations
case object requires extends RelationType
case object relatesTo extends RelationType

//Factory traits
trait RelationFactory {
  self: Entity =>
  def requires(elems: Elem*) = Relation(this, reqT.requires, Model(elems:_*))
  def requires(submodel: Model) = Relation(this, reqT.requires, submodel)
  def relatesTo(elems: Elem*) = Relation(this, reqT.relatesTo, Model(elems:_*))
  def relatesTo(submodel: Model) = Relation(this, reqT.relatesTo, submodel)
}

trait HeadFactory {
  self: Entity =>
  def requires = Head(this, reqT.requires)
  def relatesTo = Head(this, reqT.relatesTo)
}

trait HeadTypeFactory {
  self: EntityType =>
  def requires = HeadType(this, reqT.requires)
  def relatesTo = HeadType(this, reqT.relatesTo)
}

trait ImplicitFactoryObjects extends CanMakeAttr { //mixed in by package object reqT
  implicit object makeVal extends AttrMaker[Val] { def apply(s: String): Val = Val(s.toString) }

  implicit object makeGist extends AttrMaker[Gist] { def apply(s: String): Gist = Gist(s.toString) }
  implicit object makeSpec extends AttrMaker[Spec] { def apply(s: String): Spec = Spec(s.toString) }
  implicit object makeText extends AttrMaker[Text] { def apply(s: String): Text = Text(s.toString) }
  implicit object makeTitle extends AttrMaker[Title] { def apply(s: String): Title = Title(s.toString) }
  implicit object makePrio extends AttrMaker[Prio] { def apply(s: String): Prio = Prio(s.toInt) }
  implicit object makeCost extends AttrMaker[Cost] { def apply(s: String): Cost = Cost(s.toInt) }
  implicit object makeOpt extends AttrMaker[Opt] { def apply(s: String): Opt = Opt(s.toCardinality) }

  implicit class StringToCardinality(s: String) { def toCardinality = Cardinality.valueOf(s)}

  lazy val attributeFromString = Map[String, String => Attribute[_]](
    "Val" -> makeAttribute[Val] _ ,
    "Gist" -> makeAttribute[Gist] _ ,
    "Spec" -> makeAttribute[Spec] _ ,
    "Text" -> makeAttribute[Text] _ ,
    "Title" -> makeAttribute[Title] _ ,
    "Prio" -> makeAttribute[Prio] _ ,
    "Cost" -> makeAttribute[Cost] _ ,
    "Opt" -> makeAttribute[Opt] _  
  )
  lazy val entityFromString = Map[String, String => Entity](
    "Ent" -> Ent.apply _ ,
    "Section" -> Section.apply _ ,
    "Item" -> Item.apply _ ,
    "Label" -> Label.apply _ ,
    "Stakeholder" -> Stakeholder.apply _ ,
    "Product" -> Product.apply _ ,
    "System" -> System.apply _ ,
    "Subdomain" -> Subdomain.apply _ ,
    "Req" -> Req.apply _ ,
    "Idea" -> Idea.apply _ ,
    "Feature" -> Feature.apply _ ,
    "Goal" -> Goal.apply _ ,
    "Wish" -> Wish.apply _ 
  )
}

*/ //<-- REMOVE THIS LINE