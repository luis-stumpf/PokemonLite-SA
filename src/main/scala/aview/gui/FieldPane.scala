package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.layout.GridPane
import scalafx.scene.image.{ Image, ImageView }
import scalafx.scene.layout.VBox
import scalafx.scene.text.Text




class FieldPane extends VBox {


  children = List(
    new GridPane(){
      val img = new Image( "https://img.pokemondb.net/sprites/black-white/anim/normal/charizard.gif", 200, 200, true, true )
      val imgView = new ImageView( img )
      val img2 = new Image( "https://img.pokemondb.net/sprites/black-white/anim/back-normal/blastoise.gif", 200, 200, true, true )
      val imgView2 = new ImageView( img2 )
      add(imgView, 0, 1)
      add(imgView2, 1, 0)
    },
    new Text("Pokemons")
  )

}
