package com.example.twod

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect

class Bird(context: Context) {
    val res = context.resources  //讀取資源
    var BirdX:Int = res.displayMetrics.widthPixels  //讀取螢幕寬度
    var BirdY:Int = 300
    var w:Int
    var h:Int
    var image: Bitmap
    var SrcRect: Rect
    lateinit var DestRect: Rect
    init {
        image = BitmapFactory.decodeResource(res, R.drawable.bird1)
        w = image.width/8
        h = image.height/8
        BirdX -= w  //螢幕右邊飛出
        SrcRect = Rect(0, 0, image.width, image.height) //裁切
    }
    var count: Int = 1

    fun update(){
        count++
        if (count>4){
            count = 1
        }

        BirdX -= (1..30).random()
        if (BirdX<=0){
            BirdX = res.displayMetrics.widthPixels - w
        }
    }

    fun draw(canvas: Canvas) {
        update()
        when (count) {
            1 -> image = BitmapFactory.decodeResource(res, R.drawable.bird1)
            2 -> image = BitmapFactory.decodeResource(res, R.drawable.bird2)
            3 -> image = BitmapFactory.decodeResource(res, R.drawable.bird3)
            4 -> image = BitmapFactory.decodeResource(res, R.drawable.bird4)
        }

        DestRect = Rect(BirdX, BirdY, BirdX + w, BirdY + h)
        canvas.drawBitmap(image, SrcRect, DestRect, null)
    }


}
