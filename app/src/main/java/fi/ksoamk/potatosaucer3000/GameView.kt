import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.view.MotionEvent
import android.view.View
import fi.ksoamk.potatosaucer3000.R


class GameView(context: Context?) : View(context) {
    var screenWidth: Int
    var screenHeight: Int
    var newWidth: Int
    var newHeight: Int
    var boundaryX = 0
    var boundaryY = 0
    var backgroundX = 0f
    var middlelayerX = 0f
    var foregroundX = 0f
    var playerF = 0  //playerFrame
    var enemyF = 0 //EnemyFrame
    var playerarmor = 3
    var gamestate = 1
    var gamelevel = 1
    var gametimer = 0
    var playerscore = 0
    var scoretext = ""
    var scoretitle = ""
    var player = arrayOfNulls<Bitmap>(6)
    var mini = arrayOfNulls<Bitmap>(2)
    var midi = arrayOfNulls<Bitmap>(2)
    var maxi = arrayOfNulls<Bitmap>(2)
    var armorpack = arrayOfNulls<Bitmap>(2)
    var potato: Bitmap
    var balloon: Bitmap
    var droprock: Bitmap
    var spacerock: Bitmap
    var armor: Bitmap
    var backgroundlvl1: Bitmap
    var backgroundlvl2: Bitmap
    var backgroundlvl3: Bitmap
    var backgroundlvl4: Bitmap
    var backgroundlvl5: Bitmap
    var middlelayerlvl1: Bitmap
    var middlelayerlvl2: Bitmap
    var middlelayerlvl3: Bitmap
    var middlelayerlvl4: Bitmap
    var middlelayerlvl5: Bitmap
    var foregroundlvl1: Bitmap
    var foregroundlvl2: Bitmap
    var foregroundlvl3: Bitmap
    var foregroundlvl4: Bitmap
    var foregroundlvl5: Bitmap
    var gameover: Bitmap
    var nextlvl: Bitmap
    var runnable: Runnable
    val update_ms: Long = 30
    val paint = Paint().apply {
        color = WHITE
        style = Paint.Style.FILL
        textSize = 50f
        typeface = Typeface.DEFAULT
    }



    //positions
    private var mini1X: Float
    private var mini1Y: Float
    private var mini2X: Float
    private var mini2Y: Float
    private var midi1X: Float
    private var midi1Y: Float
    private var midi2X: Float
    private var midi2Y: Float
    private var maxiX: Float
    private var maxiY: Float
    private var balloonX: Float
    private var balloonY: Float
    private var armorpackX: Float
    private var armorpackY: Float
    private var potatoX: Float
    private var potatoY: Float
    private var playerX: Float
    private var playerY: Float
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var initialX = 0f
    private var initialY = 0f
    private val armorX: Float
    private val armorY: Float
    private val scoreX: Float
    private val scoreY: Float

    //game functions
    private fun resetPositions() {
        mini1X = -120f
        mini1Y = -120f
        mini2X = screenWidth.toFloat()
        mini2Y = -120f
        midi1X = screenWidth.toFloat()
        midi1Y = -120f
        midi2X = Math.floor(Math.random() * (screenWidth - midi[0]!!.width)).toFloat()
        midi2Y = -120f
        maxiX = screenWidth.toFloat()
        maxiY = -120f
        armorpackX = screenWidth.toFloat()
        armorpackY = -120f
        potatoX = screenWidth.toFloat()
        potatoY = -120f
        balloonX = Math.floor(Math.random() * (screenWidth - balloon.width)).toFloat()
        balloonY = -120f
    }
    private fun quitGame(){
        val activity = context as Activity
        activity.finish()
    }

    //enemy spawn functions
    private fun smini1(){
        mini1X += 25f
        if (mini1X > screenWidth+mini[0]!!.width) {
            mini1X = -100f
            mini1Y = Math.floor(Math.random() * (screenHeight - mini[0]!!.height)).toFloat()
        }
    }
    private fun smini2(){
        mini2X -= 25f
        if (mini2X < 0-mini[0]!!.width.toFloat()) {
            mini2X = screenWidth + (screenWidth/4).toFloat()
            mini2Y = playerY
        }
    }
    private fun smidi1(){
        midi1X -= 25f
        if (midi1X < 0-midi[0]!!.width.toFloat()) {
            midi1X = screenWidth + (screenWidth/4).toFloat()
            midi1Y = Math.floor(Math.random() * (screenHeight - midi[0]!!.height)).toFloat()
        }
    }
    private fun smidi2(){
        midi2Y -= 15f
        if (midi2Y < 0) {
            midi2X = Math.floor(Math.random() * (screenWidth - midi[0]!!.width)).toFloat()
            midi2Y = screenHeight.toFloat()
        }
    }
    private fun smaxi(){
        if (maxiX < playerX){
            maxiX += 5f
        } else if (maxiX > playerX){
            maxiX -= 5f
        }
        if (maxiY > playerY){
            maxiY -= 5f
        } else if (maxiY < playerY) {
            maxiY += 5f
        }
    }
    private fun sballoon(){
        balloonY += 8f
        if (balloonY > screenHeight) {
            balloonX = playerX + (player[0]!!.width / 2)
            balloonY = -100f
        }
    }
    private fun sarmorpack(){
        armorpackX -= 8f
        if (armorpackX < 0-armorpack[0]!!.width.toFloat()) {
            armorpackX = screenWidth + (screenWidth/4).toFloat()
            armorpackY = Math.floor(Math.random() * (screenHeight - armorpack[0]!!.height * 2)).toFloat()
        }
    }
    private fun spotato(){
        potatoX -= 20f
        if (potatoX < 0-potato.width.toFloat()) {
            potatoX = screenWidth.toFloat()
            potatoY = Math.floor(Math.random() * (screenHeight - potato.height)).toFloat()
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        boundaryX = size.x
        boundaryY = size.y
        when (gamestate) {
            1 -> when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    gamestate = 2
                    return true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_MOVE -> return true
            }
            2 -> when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = playerX
                    initialY = playerY
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    return true
                }
                MotionEvent.ACTION_UP -> return true
                MotionEvent.ACTION_MOVE -> {
                    playerX = initialX + (event.rawX - initialTouchX)
                    playerY = initialY + (event.rawY - initialTouchY)
                    if (playerX < 0) {
                        playerX = 0f
                    }
                    if (playerX > boundaryX - player[0]!!.width) {
                        playerX = (boundaryX - player[0]!!.width).toFloat()
                    }
                    if (playerY < 0) {
                        playerY = 0f
                    }
                    if (playerY > boundaryY - (player[0]!!.height*2)) {
                        playerY = (boundaryY - (player[0]!!.height*2)).toFloat()
                    }
                    return true
                }
            }
            3 -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        quitGame()
                        return true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_MOVE -> return true
                }
            }

            4 -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        gamelevel = gamelevel + 1
                        gamestate = 2
                        return true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_MOVE -> return true
                }
            }
        }
        return false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (gamestate) {
            1 -> {
                canvas.drawBitmap(backgroundlvl1, 0f, 0f, null)
                playerF++
                if (playerF == 6) {
                    playerF = 0
                } //if
                canvas.drawBitmap(player[playerF]!!, playerX, playerY, null)
            }
            2 -> {

                when (gametimer) {
                    200 -> {
                        gamestate = 4
                    }
                    400 -> {
                        gamestate = 4
                    }
                    600 -> {
                        gamestate = 4
                    }
                    800 -> {
                        gamestate = 4
                    }
                }
                gametimer++
                when (gamelevel) {
                    1 -> {//start of lvl 1
                        //background scroll
                        backgroundX -= 3 // background speed
                        if (backgroundX < -newWidth) {
                            backgroundX = 0f
                        } //if
                        canvas.drawBitmap(backgroundlvl1, backgroundX, 0f, null)
                        if (backgroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(backgroundlvl1, backgroundX + newWidth, 0f, null)
                        } //if - end of background scroll

                        //middlelayer scroll
                        middlelayerX -= 15 // middlelayer speed
                        if (middlelayerX < -newWidth) {
                            middlelayerX = 0f
                        } //if
                        canvas.drawBitmap(middlelayerlvl1, middlelayerX, 0f, null)
                        if (middlelayerX < screenWidth - newWidth) {
                            canvas.drawBitmap(middlelayerlvl1, middlelayerX + newWidth, 0f, null)
                        } //if - end of middlelayer scroll

                        //foreground scroll
                        foregroundX -= 20 // foreground speed
                        if (foregroundX < -newWidth) {
                            foregroundX = 0f
                        } //if
                        canvas.drawBitmap(foregroundlvl1, foregroundX, 0f, null)
                        if (foregroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(foregroundlvl1, foregroundX + newWidth, 0f, null)
                        } //if - end of foreground scroll
                        //end of level 1 backgrounds, start of level 1 enemies
                        smidi1()
                        canvas.drawBitmap(midi[enemyF]!!, midi1X, midi1Y, null)
                        sballoon()
                        canvas.drawBitmap(balloon, balloonX, balloonY, null)
                        //pickups
                        sarmorpack()
                        canvas.drawBitmap(armorpack[enemyF]!!, armorpackX, armorpackY, null)
                        spotato()
                        canvas.drawBitmap(potato, potatoX, potatoY, null)
                        canvas.drawText("Score: $playerscore", scoreX, scoreY, paint)
                    } //end of lvl 1

                    2 -> {//start of lvl 2
                        //background scroll
                        backgroundX -= 3 // background speed
                        if (backgroundX < -newWidth) {
                            backgroundX = 0f
                        } //if
                        canvas.drawBitmap(backgroundlvl2, backgroundX, 0f, null)
                        if (backgroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(backgroundlvl2, backgroundX + newWidth, 0f, null)
                        } //if - end of background scroll

                        //middlelayer scroll
                        middlelayerX -= 10 // middlelayer speed
                        if (middlelayerX < -newWidth) {
                            middlelayerX = 0f
                        } //if
                        canvas.drawBitmap(middlelayerlvl2, middlelayerX, 0f, null)
                        if (middlelayerX < screenWidth - newWidth) {
                            canvas.drawBitmap(middlelayerlvl2, middlelayerX + newWidth, 0f, null)
                        } //if - end of middlelayer scroll

                        //foreground scroll
                        foregroundX -= 20 // foreground speed
                        if (foregroundX < -newWidth) {
                            foregroundX = 0f
                        } //if
                        canvas.drawBitmap(foregroundlvl2, foregroundX, 0f, null)
                        if (foregroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(foregroundlvl2, foregroundX + newWidth, 0f, null)
                        } //if - end of foreground scroll
                        //end of lvl 2 backgrounds start of enemy spawns
                        smidi1()
                        canvas.drawBitmap(midi[enemyF]!!, midi1X, midi1Y, null)
                        smidi2()
                        canvas.drawBitmap(midi[enemyF]!!, midi2X, midi2Y, null)
                        sballoon()
                        canvas.drawBitmap(balloon, balloonX, balloonY, null)
                        // pickups
                        sarmorpack()
                        canvas.drawBitmap(armorpack[enemyF]!!, armorpackX, armorpackY, null)
                        spotato()
                        canvas.drawBitmap(potato, potatoX, potatoY, null)
                        canvas.drawText("Score: $playerscore", scoreX, scoreY, paint)
                    } // end of lvl 2

                    3 -> {//start of lvl 3
                        //background scroll
                        backgroundX -= 3 // background speed
                        if (backgroundX < -newWidth) {
                            backgroundX = 0f
                        } //if
                        canvas.drawBitmap(backgroundlvl3, backgroundX, 0f, null)
                        if (backgroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(backgroundlvl3, backgroundX + newWidth, 0f, null)
                        } //if - end of background scroll

                        //middlelayer scroll
                        middlelayerX -= 10 // middlelayer speed
                        if (middlelayerX < -newWidth) {
                            middlelayerX = 0f
                        } //if
                        canvas.drawBitmap(middlelayerlvl3, middlelayerX, 0f, null)
                        if (middlelayerX < screenWidth - newWidth) {
                            canvas.drawBitmap(middlelayerlvl3, middlelayerX + newWidth, 0f, null)
                        } //if - end of middlelayer scroll
                        //foreground scroll
                        foregroundX -= 25 // foreground speed
                        if (foregroundX < -newWidth) {
                            foregroundX = 0f
                        } //if
                        canvas.drawBitmap(foregroundlvl3, foregroundX, 0f, null)
                        if (foregroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(foregroundlvl3, foregroundX + newWidth, 0f, null)
                        } //if - end of foreground scroll
                        //end of lvl 3 backgrounds start of enemy spawns
                        smini1()
                        canvas.drawBitmap(mini[enemyF]!!, mini1X, mini1Y, null)
                        smidi1()
                        canvas.drawBitmap(midi[enemyF]!!, midi1X, midi1Y, null)
                        smidi2()
                        canvas.drawBitmap(midi[enemyF]!!, midi2X, midi2Y, null)
                        sballoon()
                        canvas.drawBitmap(balloon, balloonX, balloonY, null)
                        //pickups
                        sarmorpack()
                        canvas.drawBitmap(armorpack[enemyF]!!, armorpackX, armorpackY, null)
                        spotato()
                        canvas.drawBitmap(potato, potatoX, potatoY, null)
                        canvas.drawText("Score: $playerscore", scoreX, scoreY, paint)
                    } // end of lvl 3

                    4 -> {//start of lvl 4
                        //background scroll
                        backgroundX -= 5 // background speed
                        if (backgroundX < -newWidth) {
                            backgroundX = 0f
                        } //if
                        canvas.drawBitmap(backgroundlvl4, backgroundX, 0f, null)
                        if (backgroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(backgroundlvl4, backgroundX + newWidth, 0f, null)
                        } //if - end of background scroll
                        //middlelayer scroll
                        middlelayerX -= 15 // middlelayer speed
                        if (middlelayerX < -newWidth) {
                            middlelayerX = 0f
                        } //if
                        canvas.drawBitmap(middlelayerlvl4, middlelayerX, 0f, null)
                        if (middlelayerX < screenWidth - newWidth) {
                            canvas.drawBitmap(middlelayerlvl4, middlelayerX + newWidth, 0f, null)
                        } //if - end of middlelayer scroll

                        //foreground scroll
                        foregroundX -= 20 // foreground speed
                        if (foregroundX < -newWidth) {
                            foregroundX = 0f
                        } //if
                        canvas.drawBitmap(foregroundlvl4, foregroundX, 0f, null)
                        if (foregroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(foregroundlvl4, foregroundX + newWidth, 0f, null)
                        } //if - end of foreground scroll
                        //end of lvl 4 backgrounds start of enemy spawns
                        smini1()
                        canvas.drawBitmap(mini[enemyF]!!, mini1X, mini1Y, null)
                        smini2()
                        canvas.drawBitmap(mini[enemyF]!!, mini2X, mini2Y, null)
                        smidi1()
                        canvas.drawBitmap(midi[enemyF]!!, midi1X, midi1Y, null)
                        smidi2()
                        canvas.drawBitmap(midi[enemyF]!!, midi2X, midi2Y, null)
                        sballoon()
                        canvas.drawBitmap(droprock, balloonX, balloonY, null)
                        //pickups
                        sarmorpack()
                        canvas.drawBitmap(armorpack[enemyF]!!, armorpackX, armorpackY, null)
                        spotato()
                        canvas.drawBitmap(potato, potatoX, potatoY, null)
                        canvas.drawText("Score: $playerscore", scoreX, scoreY, paint)
                    } //end of lvl 4

                    5 -> {//start of lvl 5
                        //background scroll
                        backgroundX -= 3 // background speed
                        if (backgroundX < -newWidth) {
                            backgroundX = 0f
                        } //if
                        canvas.drawBitmap(backgroundlvl5, backgroundX, 0f, null)
                        if (backgroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(backgroundlvl5, backgroundX + newWidth, 0f, null)
                        } //if - end of background scroll

                        //middlelayer scroll
                        middlelayerX -= 5 // middlelayer speed
                        if (middlelayerX < -newWidth) {
                            middlelayerX = 0f
                        } //if
                        canvas.drawBitmap(middlelayerlvl5, middlelayerX, 0f, null)
                        if (middlelayerX < screenWidth - newWidth) {
                            canvas.drawBitmap(middlelayerlvl5, middlelayerX + newWidth, 0f, null)
                        } //if - end of middlelayer scroll

                        //foreground scroll
                        foregroundX -= 14 // foreground speed
                        if (foregroundX < -newWidth) {
                            foregroundX = 0f
                        } //if
                        canvas.drawBitmap(foregroundlvl5, foregroundX, 0f, null)
                        if (foregroundX < screenWidth - newWidth) {
                            canvas.drawBitmap(foregroundlvl5, foregroundX + newWidth, 0f, null)
                        } //if - end of foreground scroll
                        //end of lvl 5 backgrounds start of enemy spawns
                        smini1()
                        canvas.drawBitmap(mini[enemyF]!!, mini1X, mini1Y, null)
                        smini2()
                        canvas.drawBitmap(mini[enemyF]!!, mini2X, mini2Y, null)
                        smidi1()
                        canvas.drawBitmap(midi[enemyF]!!, midi1X, midi1Y, null)
                        smidi2()
                        canvas.drawBitmap(midi[enemyF]!!, midi2X, midi2Y, null)
                        smaxi()
                        canvas.drawBitmap(maxi[enemyF]!!, maxiX, maxiY, null)
                        sballoon()
                        canvas.drawBitmap(spacerock, balloonX, balloonY, null)
                        //pickups
                        sarmorpack()
                        canvas.drawBitmap(armorpack[enemyF]!!, armorpackX, armorpackY, null)
                        spotato()
                        canvas.drawBitmap(potato, potatoX, potatoY, null)
                        canvas.drawText("Score: $playerscore", scoreX, scoreY, paint)
                    } //end of lvl 5
                }


                //player animation
                playerF++
                if (playerF == 6) {
                    playerF = 0
                } //if
                enemyF++
                if (enemyF == 2) {
                    enemyF = 0
                } //if
                canvas.drawBitmap(player[playerF]!!, playerX, playerY, null)


                //hit detection
                if (balloonX < playerX + player[0]!!.width && balloonX + balloon.width > playerX
                        && balloonY < playerY + player[0]!!.height && balloonY + balloon.height > playerY
                ) {
                    balloonY = -150f
                    balloonX = playerX
                    playerarmor -= 2
                    if (playerarmor < 0) {
                        playerarmor = 0
                    }
                }//end of balloon hit start of mini hit
                if (mini1X < playerX + player[0]!!.width && mini1X + mini[0]!!.width > playerX
                        && mini1Y < playerY + player[0]!!.height && mini1Y + mini[0]!!.height > playerY
                ) {
                    mini1Y = Math.floor(Math.random() * (screenHeight - mini[0]!!.height)).toFloat()
                    mini1X = 0 - mini[0]!!.width.toFloat()
                    playerarmor -= 1
                }
                if (mini2X < playerX + player[0]!!.width && mini2X + mini[0]!!.width > playerX
                        && mini2Y < playerY + player[0]!!.height && mini2Y + mini[0]!!.height > playerY
                ) {
                    mini2Y = Math.floor(Math.random() * (screenHeight - mini[0]!!.height)).toFloat()
                    mini2X = screenWidth + midi[0]!!.width.toFloat()
                    playerarmor -= 1
                }//end of mini hit start of midi hit
                if (midi1X < playerX + player[0]!!.width && midi1X + midi[0]!!.width > playerX
                        && midi1Y < playerY + player[0]!!.height && midi1Y + midi[0]!!.height > playerY
                ) {
                    midi1Y = Math.floor(Math.random() * (screenHeight - midi[0]!!.height)).toFloat()
                    midi1X = screenWidth + midi[0]!!.width.toFloat()
                    playerarmor -= 1
                }
                if (midi2X < playerX + player[0]!!.width && midi2X + midi[0]!!.width > playerX
                        && midi2Y < playerY + player[0]!!.height && midi2Y + midi[0]!!.height > playerY
                ) {
                    midi2Y = screenHeight + screenHeight / 4.toFloat()
                    midi2X = Math.floor(Math.random() * (screenWidth - midi[0]!!.width)).toFloat()
                    playerarmor -= 1
                } //end of midi hit start of maxi hit
                if (maxiX < playerX + player[0]!!.width && maxiX + maxi[0]!!.width > playerX
                        && maxiY < playerY + player[0]!!.height && maxiY + maxi[0]!!.height > playerY
                ) {
                    playerarmor = 0
                }
                //pickups
                if (armorpackX < playerX + player[0]!!.width && armorpackX + armorpack[0]!!.width > playerX
                        && armorpackY < playerY + player[0]!!.height && armorpackY + armorpack[0]!!.height > playerY
                ) {
                    armorpackY = Math.floor(Math.random() * (screenHeight - midi[0]!!.width)).toFloat()
                    armorpackX = screenWidth * 2.toFloat()
                    if (playerarmor < 5) {
                        playerarmor += 1
                    } else {
                        playerscore += 200
                    }
                }
                if (potatoX < playerX + player[0]!!.width && potatoX + potato.width > playerX
                        && potatoY < playerY + player[0]!!.height && potatoY + potato.height > playerY
                ) {
                    potatoY = Math.floor(Math.random() * (screenHeight - midi[0]!!.width)).toFloat()
                    potatoX = screenWidth + potato.width.toFloat()
                    playerscore += 50
                }

                when (playerarmor) {
                    0 -> gamestate = 3
                    1 -> canvas.drawBitmap(armor, armorX, armorY, null)
                    2 -> {
                        canvas.drawBitmap(armor, armorX, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width + 5, armorY, null)
                    }
                    3 -> {
                        canvas.drawBitmap(armor, armorX, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width + 5, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width * 2 + 10, armorY, null)
                    }
                    4 -> {
                        canvas.drawBitmap(armor, armorX, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width + 5, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width * 2 + 10, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width * 3 + 15, armorY, null)
                    }
                    5 -> {
                        canvas.drawBitmap(armor, armorX, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width + 5, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width * 2 + 10, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width * 3 + 15, armorY, null)
                        canvas.drawBitmap(armor, armorX + armor.width * 4 + 20, armorY, null)
                    }
                }
            }
            3 -> {
                canvas.drawBitmap(gameover, 0f, 0f, null)
                scoretext ="Final Score: $playerscore"
                scoretitle = "GAME OVER"

                canvas.drawText(scoretext, (screenWidth/2)-paint.measureText(scoretext), screenHeight/2.toFloat(), paint)
                canvas.drawText(scoretitle, (screenWidth/2)-paint.measureText(scoretitle), screenHeight/2-paint.textSize*2,paint)
            }
            4 -> {
                canvas.drawBitmap(nextlvl, 0f, 0f, null)
                resetPositions()
                playerX = (screenWidth / 2)-(player[0]!!.width/2).toFloat()
                playerY = (screenHeight / 2)+(player[0]!!.height*2).toFloat()
                playerF++
                if (playerF == 6) {
                    playerF = 0
                }
                canvas.drawBitmap(player[playerF]!!, playerX, playerY, null)
                scoretext ="Current Score: $playerscore"
                scoretitle = "Flying to next level"
                canvas.drawText(scoretext, (screenWidth/2)-(paint.measureText(scoretext)/2), screenHeight/2.toFloat(), paint)
                canvas.drawText(scoretitle, (screenWidth/2)-(paint.measureText(scoretitle)/2), screenHeight/2-paint.textSize*2,paint)
            }
        }

        handler.postDelayed(runnable, update_ms)
    } //onDraw

    init {
        //setup images
        gameover = BitmapFactory.decodeResource(resources, R.drawable.gameover)
        nextlvl = BitmapFactory.decodeResource(resources, R.drawable.nextlvl)
        armor = BitmapFactory.decodeResource(resources, R.drawable.armor)
        //background
        backgroundlvl1 = BitmapFactory.decodeResource(resources, R.drawable.cloudsday)
        backgroundlvl2 = BitmapFactory.decodeResource(resources, R.drawable.nightstars)
        backgroundlvl3 = BitmapFactory.decodeResource(resources, R.drawable.cloudsday)
        backgroundlvl4 = BitmapFactory.decodeResource(resources, R.drawable.tunnel)
        backgroundlvl5 = BitmapFactory.decodeResource(resources, R.drawable.space)
        //middlelayer
        middlelayerlvl1 = BitmapFactory.decodeResource(resources, R.drawable.forest)
        middlelayerlvl2 = BitmapFactory.decodeResource(resources, R.drawable.city)
        middlelayerlvl3 = BitmapFactory.decodeResource(resources, R.drawable.mountains)
        middlelayerlvl4 = BitmapFactory.decodeResource(resources, R.drawable.pylons)
        middlelayerlvl5 = BitmapFactory.decodeResource(resources, R.drawable.moon)
        //foreground
        foregroundlvl1 = BitmapFactory.decodeResource(resources, R.drawable.grass)
        foregroundlvl2 = BitmapFactory.decodeResource(resources, R.drawable.road)
        foregroundlvl3 = BitmapFactory.decodeResource(resources, R.drawable.forest)
        foregroundlvl4 = BitmapFactory.decodeResource(resources, R.drawable.rocks)
        foregroundlvl5 = BitmapFactory.decodeResource(resources, R.drawable.asteroids)
        //following bombs
        balloon = BitmapFactory.decodeResource(resources, R.drawable.balloon)
        droprock = BitmapFactory.decodeResource(resources, R.drawable.droprock)
        spacerock = BitmapFactory.decodeResource(resources, R.drawable.spacerock)
        //mini enemy
        mini[0] = BitmapFactory.decodeResource(resources, R.drawable.mini1)
        mini[1] = BitmapFactory.decodeResource(resources, R.drawable.mini2)
        //midi enemy
        midi[0] = BitmapFactory.decodeResource(resources, R.drawable.midi1)
        midi[1] = BitmapFactory.decodeResource(resources, R.drawable.midi2)
        //maxi enemy
        maxi[0] = BitmapFactory.decodeResource(resources, R.drawable.maxi1)
        maxi[1] = BitmapFactory.decodeResource(resources, R.drawable.maxi2)
        //player
        player[0] = BitmapFactory.decodeResource(resources, R.drawable.player1)
        player[1] = BitmapFactory.decodeResource(resources, R.drawable.player2)
        player[2] = BitmapFactory.decodeResource(resources, R.drawable.player3)
        player[3] = BitmapFactory.decodeResource(resources, R.drawable.player2)
        player[4] = BitmapFactory.decodeResource(resources, R.drawable.player1)
        player[5] = BitmapFactory.decodeResource(resources, R.drawable.player2)
        //pickups
        armorpack[0] = BitmapFactory.decodeResource(resources, R.drawable.armorpack1)
        armorpack[1] = BitmapFactory.decodeResource(resources, R.drawable.armorpack2)
        potato = BitmapFactory.decodeResource(resources, R.drawable.potato)

        val display = (getContext() as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
        val height = backgroundlvl1.height.toFloat()
        val width = backgroundlvl1.width.toFloat()
        val ratio = width / height
        newHeight = screenHeight
        newWidth =  (ratio * screenHeight).toInt()
        // create scaled bitmaps for the backgrounds
        //1st lvl
        backgroundlvl1 = Bitmap.createScaledBitmap(backgroundlvl1, newWidth, newHeight, false)
        middlelayerlvl1 = Bitmap.createScaledBitmap(middlelayerlvl1, newWidth, newHeight, false)
        foregroundlvl1 = Bitmap.createScaledBitmap(foregroundlvl1, newWidth, newHeight, false)
        //2nd lvl
        backgroundlvl2 = Bitmap.createScaledBitmap(backgroundlvl2, newWidth, newHeight, false)
        middlelayerlvl2 = Bitmap.createScaledBitmap(middlelayerlvl2, newWidth, newHeight, false)
        foregroundlvl2 = Bitmap.createScaledBitmap(foregroundlvl2, newWidth, newHeight, false)
        //3rd lvl
        backgroundlvl3 = Bitmap.createScaledBitmap(backgroundlvl3, newWidth, newHeight, false)
        middlelayerlvl3 = Bitmap.createScaledBitmap(middlelayerlvl3, newWidth, newHeight, false)
        foregroundlvl3 = Bitmap.createScaledBitmap(foregroundlvl3, newWidth, newHeight, false)
        //4th lvl
        backgroundlvl4 = Bitmap.createScaledBitmap(backgroundlvl4, newWidth, newHeight, false)
        middlelayerlvl4 = Bitmap.createScaledBitmap(middlelayerlvl4, newWidth, newHeight, false)
        foregroundlvl4 = Bitmap.createScaledBitmap(foregroundlvl4, newWidth, newHeight, false)
        //5th lvl
        backgroundlvl5 = Bitmap.createScaledBitmap(backgroundlvl5, newWidth, newHeight, false)
        middlelayerlvl5 = Bitmap.createScaledBitmap(middlelayerlvl5, newWidth, newHeight, false)
        foregroundlvl5 = Bitmap.createScaledBitmap(foregroundlvl5, newWidth, newHeight, false)
        //end of lvls
        //initial locations for player and enemies
        playerX = (screenWidth / 4).toFloat()
        playerY = (screenHeight / 2).toFloat()
        mini1X = -120f
        mini1Y = -120f
        mini2X = screenWidth.toFloat()
        mini2Y = -120f
        midi1X = screenWidth.toFloat()
        midi1Y = -120f
        midi2X = Math.floor(Math.random() * (screenWidth - midi[0]!!.width)).toFloat()
        midi2Y = -120f
        maxiX = screenWidth.toFloat()
        maxiY = -120f
        armorpackX = screenWidth.toFloat()
        armorpackY = -120f
        potatoX = screenWidth.toFloat()
        potatoY = -120f
        balloonX = Math.floor(Math.random() * (screenWidth - balloon.width)).toFloat()
        balloonY = -120f
        armorX = 30f
        armorY = 30f
        scoreX = 30f
        scoreY = armor.height * 2.toFloat()
        runnable = Runnable { invalidate() } //runnable
    } // init
} //Class GameView

