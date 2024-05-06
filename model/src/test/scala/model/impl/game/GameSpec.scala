package model.impl.game

import model.PokemonType.{Glurak, Simsala}
import model.impl.pokePlayer.PokePlayer
import model.State.*
import model.{Attack, PokePack, Pokemon, PokemonArt, State}
import util.{NoValidAttackSelected, NotEnoughPokemonSelected, HorriblePlayerNameError}

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.util.Failure

class GameSpec extends AnyWordSpec {
  "The Game Class" when {

    "checkForValidNameInput is called with an empty string" should {
      "return a Failure with NoInput" in {
        val game = new Game()
        val result = game.checkForValidNameInput("")

        result.isFailure shouldBe true
        //result.failed.get shouldBe a[NoInput]
      }
    }

    "checkForValidNameInput is called with a string longer than maxPlayerNameLength" should {
      "return a Failure with NameTooLong" in {
        val game = new Game()
        val longName = "a" * (Game.maxPlayerNameLength + 1)
        val result = game.checkForValidNameInput(longName)

        result.isFailure shouldBe true
        //result.failed.get shouldBe a[NameTooLong]
      }
    }

    "assignTheCorrectPlayerA is called when both player1 and player2 are None" should {
      "return a Failure with HorriblePokemonSelectionError" in {
        val game = Game(player1 = None, player2 = None, turn = 1)

        val result = game.assignTheCorrectPlayerA(List(Some(Pokemon(Glurak, 150))))

        result.isFailure shouldBe true
        //result.failed.get shouldBe a[HorriblePokemonSelectionError]
      }
    }

    "assignTheCorrectPlayerA is called when player1 has pokemons equal to PokePack(List(None)) and player2 has pokemons not equal to PokePack(List(None))" should {
      "return a Failure with HorriblePokemonSelectionError" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(None))))
        val player2 = Some(PokePlayer("Player2", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val game = Game(player1 = player1, player2 = player2, turn = 1)

        val result = game.assignTheCorrectPlayerA(List(Some(Pokemon(Glurak, 150))))

        result.isFailure shouldBe true
        //result.failed.get shouldBe a[HorriblePokemonSelectionError]
      }
    }

    "assignTheCorrectPlayerA is called when player1 is None and player2 is Some(PokePlayer)" should {
      "return a Failure with HorriblePlayerNameError" in {
        val player2 = Some(PokePlayer("Player2", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val game = Game(player1 = None, player2 = player2, turn = 1)

        val result = game.assignTheCorrectPlayerA("Player1")

        result.isFailure shouldBe true
        //result.failed.get shouldBe a[HorriblePlayerNameError]
      }
    }

    "checkSizeOf is called with a list containing less than maxPokePackSize non-empty elements" should {
      "return a Failure with NotEnoughPokemonSelected" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val game = Game(player1 = player1, player2 = None, turn = 3)

        val pokeList = List(Some(Pokemon(Glurak, 150)), None, None) // Less than Game.maxPokePackSize non-empty elements

        val result = game.checkSizeOf(pokeList)

        result.isFailure shouldBe true
        result.failed.get shouldBe a[NotEnoughPokemonSelected]
      }
    }

    "selectPokemonFrom is called with valid input not covered by specific cases" should {
      "return a Success" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val game = Game(player1 = player1, player2 = None, turn = 3)

        val result = game.selectPokemonFrom("3")

        result.isSuccess shouldBe true
      }
    }

    "selectPokemonFrom is called with invalid input not covered by specific cases" should {
      "return a Failure" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val game = Game(player1 = player1, player2 = None, turn = 1)

        val result = game.selectPokemonFrom("42")

        result.isFailure shouldBe true
      }
    }

    "selectedPokemonFrom is called with valid input" should {
      "return a Game with the selected Pokemon for the current player" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val game = Game(player1 = player1, player2 = None, turn = 1)

        val result = game.selectPokemonFrom("1")

        result.isSuccess shouldBe true
        result.get.player1.flatMap(_.currentPokemon) shouldBe Some(Pokemon(Glurak, 150))
      }
    }

    "selectedPokemonFrom is called with invalid input" should {
      "return a Failure" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val game = Game(player1 = player1, player2 = None, turn = 1)

        val result = game.selectPokemonFrom("invalid input")

        result.isFailure shouldBe true
      }
    }

    "currentPokemonIsDead is called with a live Pokemon" should {
      "trigger the case _" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val game = Game(player1 = player1, player2 = None, turn = 3) // Set turn to a value other than 1 or 2, and player2 to None

        val result = game.currentPokemonIsDead

        result shouldBe false
      }
    }

    "interpretAttackSelectionFrom is called with invalid attack selection" should {
      "return Failure with NoValidAttackSelected" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val player2 = Some(PokePlayer("Player2", PokePack(List(Some(Pokemon(Simsala, 130))))))
        val game = Game(player1 = player1, player2 = player2, turn = 44)

        print(" NOW fr hhheeeere we go ")
        val resultWithInvalidInput2 = game.interpretAttackSelectionFrom("99")

        resultWithInvalidInput2 match {
          case Failure(exception) => assert(exception.isInstanceOf[NoValidAttackSelected])
          case _ => fail("Expected a Failure")
        }
      }
    }

    "interpretAttackSelectionFrom is called" should {
      "handle unexpected input" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val player2 = Some(PokePlayer("Player2", PokePack(List(Some(Pokemon(Simsala, 130))))))
        val game = Game(player1 = player1, player2 = player2)

        val resultWithInvalidInput = game.interpretAttackSelectionFrom("invalid input")

        assert(resultWithInvalidInput.isFailure)
      }
    }

    "removePlayer is called" should {
      "set player2 to None, state to InitPlayerState, and turn to 2" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val player2 = Some(PokePlayer("Player2", PokePack(List(Some(Pokemon(Simsala, 130))))))
        val game = Game(player1 = player1, player2 = player2, turn = 1)

        val updatedGame = game.removePlayer()

        updatedGame.player2 shouldBe None
        updatedGame.state shouldBe InitPlayerState
        updatedGame.turn shouldBe 2
      }
    }

    "setNextTurn is called" should {
      "set turn to 1 if current turn is not 1" in {
        val game = Game(turn = 2)
        val updatedGame = game.setNextTurn()
        updatedGame.turn shouldBe 1
      }
    }

    "converted to XML" should {
      "produce the correct XML structure" in {
        val game = Game(
          state = InitState,
          player1 = Some(PokePlayer("Player1")),
          player2 = Some(PokePlayer("Player2")),
          turn = 1
        )
        val xml = game.toXML

        (xml \ "state").text should be("InitState")
        (xml \ "player1").text.replaceAll("\\s", "") should be("Player1None10")
        (xml \ "player2").text.replaceAll("\\s", "") should be("Player2None10")
        (xml \ "turn").text.replaceAll("\\s", "") should be("1")
      }
    }

    "setWinner is called" should {
      "not assign a winner if either player is None" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val gameWithPlayer1Only = Game(player1 = player1, player2 = None)
        val gameWithPlayer2Only = Game(player1 = None, player2 = player1)
        val gameWithNoPlayers = Game(player1 = None, player2 = None)

        gameWithPlayer1Only.setWinner().winner shouldBe None
        gameWithPlayer2Only.setWinner().winner shouldBe None
        gameWithNoPlayers.setWinner().winner shouldBe None
      }
    }

    "no winner is determined" should {
      "return false for hasWinner" in {
        val game = Game()
        game.hasWinner shouldBe false
      }
    }

    "a winner is determined" should {
      "return true for hasWinner" in {
        val winner = PokePlayer( "Winner" )
        val game = Game( winner = Some( winner ) )
        game.hasWinner shouldBe true
      }
    }

    "empty" should {
      var game = Game()
      "have the InitState" in {
        assert( game.state == InitState )
      }
      "have no players" in {
        assert( game.player1 == None && game.player2 == None )
      }
      "be able to set the state" in {
        game.setStateTo( InitPlayerState ) should be( Game( InitPlayerState ) )
      }
      "be able to add a Player" in {
        game.addPlayerWith( "Luis" ).get should be(
          Game( InitPlayerState, Some( PokePlayer( "Luis" ) ) ).setNextTurn()
        )
      }
      "be able to remove a Player" in {
        game = game.addPlayerWith( "Luis" ).get
        game.removePlayer() should be( Game( InitPlayerState ) )
      }
      "be able to remove a Pokemon from a Player 1" in {
        game = game.addPlayerWith( "timmy" ).get
        game = game.interpretPokemonSelectionFrom( "111" ).get
        game.removePokemonFromPlayer() should be(
          Game(
            InitPlayerPokemonState,
            Some( PokePlayer( "Luis" ) ),
            Some( PokePlayer( "timmy" ) )
          )
        )
      }
      "be able to remove a Pokemon from a Player 2" in {
        game = game.interpretPokemonSelectionFrom( "123" ).get
        game.removePokemonFromPlayer() should be(
          Game(
            InitPlayerPokemonState,
            Some(
              PokePlayer(
                "Luis",
                PokePack(
                  List(
                    Some( Pokemon.apply( Glurak ) ),
                    Some( Pokemon.apply( Glurak ) ),
                    Some( Pokemon.apply( Glurak ) )
                  )
                )
              )
            ),
            Some( PokePlayer( "timmy" ) )
          ).setNextTurn()
        )
      }
      "be able to attack a player 1" in {
        game = Game(
          FightingState,
          Some(
            PokePlayer(
              "Luis",
              PokePack( List( Some( Pokemon.apply( Simsala ) ) ) )
            )
          ),
          Some(
            PokePlayer(
              "Timmy",
              PokePack( List( Some( Pokemon.apply( Glurak ) ) ) )
            )
          )
        )
        game.interpretAttackSelectionFrom( "1" ).get should be {
          Game(
            FightingState,
            Some(
              PokePlayer(
                "Luis",
                PokePack( List( Some( Pokemon.apply( Simsala ) ) ) )
              )
            ),
            Some(
              PokePlayer(
                "Timmy",
                PokePack(
                  List(
                    Some( Some( Pokemon.apply( Glurak ) ).get.reduceHP( 10.0 ) )
                  )
                )
              )
            )
          ).setNextTurn()

        }
      }
      "be able to reverse attack a player 1" in {
        game = Game(
          FightingState,
          Some(
            PokePlayer(
              "Luis",
              PokePack(
                List(
                  Some( Some( Pokemon.apply( Simsala ) ).get.reduceHP( 20.0 ) )
                )
              )
            )
          ),
          Some(
            PokePlayer(
              "Timmy",
              PokePack( List( Some( Pokemon.apply( Glurak ) ) ) )
            )
          )
        )
        game.reverseAttackWith( "1" ) should be {
          Game(
            FightingState,
            Some(
              PokePlayer(
                "Luis",
                PokePack( List( Some( Pokemon.apply( Simsala ) ) ) )
              )
            ),
            Some(
              PokePlayer(
                "Timmy",
                PokePack( List( Some( Pokemon.apply( Glurak ) ) ) )
              )
            )
          ).setNextTurn()

        }
      }
      "be able to attack a player 2" in {
        game = Game(
          FightingState,
          Some(
            PokePlayer(
              "Luis",
              PokePack( List( Some( Pokemon.apply( Glurak ) ) ) )
            )
          ),
          Some(
            PokePlayer(
              "Timmy",
              PokePack( List( Some( Pokemon.apply( Simsala ) ) ) )
            )
          )
        ).setNextTurn()
        game.interpretAttackSelectionFrom( "1" ).get should be {
          Game(
            FightingState,
            Some(
              PokePlayer(
                "Luis",
                PokePack(
                  List(
                    Some( Some( Pokemon.apply( Glurak ) ).get.reduceHP( 10.0 ) )
                  )
                )
              )
            ),
            Some(
              PokePlayer(
                "Timmy",
                PokePack( List( Some( Pokemon.apply( Simsala ) ) ) )
              )
            )
          )

        }
      }
      // TODO: Testen ob AttackPlayerStrat.strategy die richtige entscheidung trifft.
      "be able to reverse attack a player 2" in {
        game = Game(
          FightingState,
          Some(
            PokePlayer(
              "Luis",
              PokePack( List( Some( Pokemon.apply( Glurak ) ) ) )
            )
          ),
          Some(
            PokePlayer(
              "Timmy",
              PokePack(
                List( Some( Pokemon.apply( Simsala ).reduceHP( 20.0 ) ) )
              )
            )
          )
        ).setNextTurn()
        game.interpretAttackSelectionFrom( "1" )
        game.reverseAttackWith( "1" ) should be {
          Game(
            FightingState,
            Some(
              PokePlayer(
                "Luis",
                PokePack( List( Some( Pokemon.apply( Glurak ) ) ) )
              )
            ),
            Some(
              PokePlayer(
                "Timmy",
                PokePack( List( Some( Pokemon.apply( Simsala ) ) ) )
              )
            )
          )
        }
      }
    }
    "toString" should {
      "return a string representation of the game" in {
        val game = Game()
        game.toString shouldBe a[String]
      }
    }

  }
  "The Game Object" should {

    "be able to create a new Game" in {
      Game().state should be( InitState )
    }

    "correctly parse XML to a Game" in {
      val xml =
        <game>
          <state>InitState</state>
          <player1>
            <name>Player1</name>
            <pokemons>
              <PokePack>
                <contents>
                  <entry>
                    <pokemon>
                      <pType>Glurak</pType>
                      <hp>150</hp>
                      <isDead>false</isDead>
                    </pokemon>
                  </entry>
                </contents>
                <size>1</size>
              </PokePack>
            </pokemons>
            <currentPoke>0</currentPoke>
          </player1>
          <player2>
            <name>Player2</name>
            <pokemons>
              <PokePack>
                <contents>
                  <entry>
                    <pokemon>
                      <pType>Glurak</pType>
                      <hp>150</hp>
                      <isDead>false</isDead>
                    </pokemon>
                  </entry>
                </contents>
                <size>1</size>
              </PokePack>
            </pokemons>
            <currentPoke>0</currentPoke>
          </player2>
          <turn>1</turn>
        </game>

      val expectedGame = Game(
        state = InitState,
        player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150)))))),
        player2 = Some(PokePlayer("Player2", PokePack(List(Some(Pokemon(Glurak, 150)))))),
        turn = 1
      )

      val result = Game.fromXML(xml)

      result shouldBe expectedGame
    }

    /*"correctly serialize and deserialize from XML" in {
      val game = Game(
      state = InitState,
      player1 = Some(PokePlayer("Player1")),
      player2 = Some(PokePlayer("Player2")),
      turn = 1
    )
      val xml = game.toXML
      //print("XMLLLL: " + xml)
      val deserializedGame = Game.fromXML(xml)
      game should be(deserializedGame)
    }*/

    "correctly serialize and deserialize from JSON" in {
      val game = Game()
      val json = game.toJson
      val deserializedGame = Game.fromJson(json)
      game should be(deserializedGame)
    }

    "check if its current state is a match state" in {
      Game.isIngame( InitState ) should be( false )
    }

    "return a damage Multiplikator with water and water" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Wasser,
        PokemonArt.Wasser
      ) should be( 1 )
    }
    "return a damage Multiplikator with water and feuer" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Wasser,
        PokemonArt.Feuer
      ) should be( 1.2 )
    }
    "return a damage Multiplikator with water and blatt" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Wasser,
        PokemonArt.Blatt
      ) should be( 0.5 )
    }
    "return a damage Multiplikator with water and Psycho" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Wasser,
        PokemonArt.Psycho
      ) should be( 1 )
    }

    "return a damage Multiplikator with fire and water" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Feuer,
        PokemonArt.Wasser
      ) should be( 0.5 )
    }
    "return a damage Multiplikator with fire and feuer" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Feuer,
        PokemonArt.Feuer
      ) should be( 1 )
    }
    "return a damage Multiplikator with fire and blatt" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Feuer,
        PokemonArt.Blatt
      ) should be( 1.3 )
    }
    "return a damage Multiplikator with fire and Psycho" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Feuer,
        PokemonArt.Psycho
      ) should be( 1 )
    }

    "return a damage Multiplikator with blatt and water" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Blatt,
        PokemonArt.Wasser
      ) should be( 1.1 )
    }
    "return a damage Multiplikator with blatt and feuer" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Blatt,
        PokemonArt.Feuer
      ) should be( 1.3 )
    }
    "return a damage Multiplikator with blatt and blatt" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Blatt,
        PokemonArt.Blatt
      ) should be( 1 )
    }
    "return a damage Multiplikator with blatt and Psycho" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Blatt,
        PokemonArt.Psycho
      ) should be( 1.2 )
    }

    "return a damage Multiplikator with psycho and water" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Psycho,
        PokemonArt.Wasser
      ) should be( 1 )
    }
    "return a damage Multiplikator with psycho and feuer" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Psycho,
        PokemonArt.Feuer
      ) should be( 1 )
    }
    "return a damage Multiplikator with psycho and blatt" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Psycho,
        PokemonArt.Blatt
      ) should be( 1 )
    }
    "return a damage Multiplikator with psycho and Psycho" in {
      Game.calculateDamageMultiplicator(
        PokemonArt.Psycho,
        PokemonArt.Psycho
      ) should be( 0.7 )
    }
    "have a poke Pack size set to a number" in {
      assert( Game.maxPokePackSize.isValidInt )
    }
  }
  "Game auxiliary constructor" should
    {
      "initialize state to InitState and turn to 1" in {
        val game = new Game()
        game.state should be(InitState)
        game.turn should be(1)
      }
    }
  /*"The ReverseAttack object" when {
    "p1_attacked_p2 is called with a known attackNumber and known Pokemon types for player1 and player2" should {
      "increase the health of player2's Pokemon by the expected amount" in {
        val player1 = Some(PokePlayer("Player1", PokePack(List(Some(Pokemon(Glurak, 150))))))
        val player2 = Some(PokePlayer("Player2", PokePack(List(Some(Pokemon(Simsala, 130))))))
        val game = Game(player1 = player1, player2 = player2, turn = 1)

        val attackNumber = 1 // Known attackNumber
        val expectedDamage = player1.flatMap(_.currentPokemon).map(_.damageOf(attackNumber)).getOrElse(0.0) * Game.calculateDamageMultiplicator(PokemonArt.Feuer, PokemonArt.Psycho)
        val expectedHealth = player2.flatMap(_.currentPokemon).map(_.health + expectedDamage).getOrElse(0.0)

        val updatedGame = game.ReverseAttack.p1_attacked_p2(attackNumber)

        updatedGame.player2.flatMap(_.currentPokemon).map(_.health) shouldBe Some(expectedHealth)
      }
    }
  }*/
}

