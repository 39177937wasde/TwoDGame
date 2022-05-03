package com.example.twod


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlinx.coroutines.GlobalScope
import javax.security.auth.callback.Callback

class MySurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),SurfaceHolder.Callback {
    var surfaceHolder: SurfaceHolder
    lateinit var BG:Bitmap
    lateinit var SuperMan: Bitmap
    var Player:Bitmap
    var PlayerX:Float=0f
    var PlayerY:Float=0f
    var Score:Int = 0  //成績
    var Shooting:Int = 0
    var BGmoveX:Int=0
    var xPos:Int = 0
    var yPos:Int = 0
    var deltaX:Int = 5
    var deltaY:Int = 5
    var bird1:Bird
    var bird2:Bird
    var bird3:Bird
    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.back)
        SuperMan = BitmapFactory.decodeResource(getResources(), R.drawable.superman)
        surfaceHolder.addCallback(this)
        Player = BitmapFactory.decodeResource(getResources(), R.drawable.player)
        bird1=Bird(context!!)
        bird2=Bird(context!!)
        bird2.BirdY=100
        bird3=Bird(context!!)
        bird3.BirdY=450

    }
    override fun surfaceCreated(p0: SurfaceHolder) {
        var canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)

    }
    fun drawSomething(canvas:Canvas) {
        //canvas.drawBitmap(BG, 0f, 0f, null)
        BGmoveX-=10
        var BGnewX:Int=BG.width+BGmoveX
        if (BGnewX <= 0) {
            BGmoveX = 0
            // only need one draw
            canvas.drawBitmap(BG, BGmoveX.toFloat(), 0f, null)
        } else {
            // need to draw original and wrap
            canvas.drawBitmap(BG, BGmoveX.toFloat(), 0f, null)
            canvas.drawBitmap(BG, BGnewX.toFloat(), 0f, null)
        }
        var SrcRect:Rect = Rect(0, 0, SuperMan.width, SuperMan.height) //裁切
        var w:Int = SuperMan.width / 6
        var h:Int = SuperMan.height / 6
        //var DestRect:Rect = Rect(0, 0, w, h)
        xPos += deltaX
        yPos += deltaY
        if (xPos >= getWidth()-w || xPos<=0){
            deltaX*=-1
        }
        if (yPos >= getHeight()-h || yPos<=0){
            deltaY*=-1
        }

        //var DestRect:Rect = Rect(0, 0, w, h)
        var DestRect:Rect = Rect(xPos, yPos, w + xPos, h + yPos)
        if (Shooting>0){
            Shooting--
        }
        else {
            canvas.drawBitmap(SuperMan, SrcRect, DestRect, null)
        }
        canvas.drawBitmap(Player, PlayerX, PlayerY, null)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLUE
        paint.textSize = 50f
        canvas.drawText("Score:"+Score.toString(), 50f,50f, paint)
        bird1.draw(canvas)
        bird2.draw(canvas)
        bird3.draw(canvas)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        PlayerX = event!!.x
        PlayerY = event!!.y
        var w:Int = SuperMan.width / 6
        var h:Int = SuperMan.height / 6
        if ((PlayerX>=xPos) && (PlayerX<=xPos+w) && (PlayerY>=yPos) && (PlayerY<=yPos+h)){
            Score++
            Shooting = 10
        }
        PlayerX -= Player.width / 2
        PlayerY -= Player.height / 2
        return false
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

}