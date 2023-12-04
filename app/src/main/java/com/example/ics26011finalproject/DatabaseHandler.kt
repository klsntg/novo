package com.example.ics26011finalproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val password: String
)
data class Category(
    val id: Int,
    val category: String
)
data class Details(
    val id: Int,
    val title: String,
    val author: String,
    val category: String,
    val imageSource: String,
    val rating: String,
    val price: String,
    val description: String,
    val addToLibrary: Boolean,
    val addToFave: Boolean
)

class DatabaseHandler (context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase.db"

        private const val TABLE_USERS = "users"
        private const val KEY_EMAIL = "email_address"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"

        private const val TABLE_CATEGORIES = "categories"
        private const val KEY_CATEGORY_ID = "id"
        private const val KEY_CATEGORY_NAME = "category"

        private const val TABLE_DETAILS = "details"
        private const val KEY_DETAILS_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_AUTHOR = "author"
        private const val KEY_CATEGORY_ID_FK = "category_id_fk"
        private const val KEY_IMAGE_SOURCE = "image_source"
        private const val KEY_RATING = "rating"
        private const val KEY_PRICE = "price"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_ADD_TO_LIBRARY = "add_to_library"
        private const val KEY_ADD_TO_FAVE = "add_to_fave"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS (" +
                "$KEY_EMAIL TEXT PRIMARY KEY," +
                "$KEY_FIRST_NAME TEXT," +
                "$KEY_LAST_NAME TEXT," +
                "$KEY_USERNAME TEXT," +
                "$KEY_PASSWORD TEXT)")

        val CREATE_CATEGORIES_TABLE = ("CREATE TABLE $TABLE_CATEGORIES (" +
                "$KEY_CATEGORY_ID INTEGER PRIMARY KEY," +
                "$KEY_CATEGORY_NAME TEXT)")

        val CREATE_DETAILS_TABLE = ("CREATE TABLE $TABLE_DETAILS (" +
                "$KEY_DETAILS_ID INTEGER PRIMARY KEY," +
                "$KEY_TITLE TEXT," +
                "$KEY_AUTHOR TEXT," +
                "$KEY_CATEGORY_ID_FK INTEGER," +
                "$KEY_IMAGE_SOURCE TEXT," +
                "$KEY_RATING TEXT," +
                "$KEY_PRICE TEXT," +
                "$KEY_DESCRIPTION TEXT," +
                "$KEY_ADD_TO_LIBRARY INTEGER, " +
                "$KEY_ADD_TO_FAVE INTEGER, " +
                "FOREIGN KEY($KEY_CATEGORY_ID_FK) REFERENCES $TABLE_CATEGORIES($KEY_CATEGORY_ID))")

        db?.execSQL(CREATE_USERS_TABLE)
        db?.execSQL(CREATE_CATEGORIES_TABLE)
        db?.execSQL(CREATE_DETAILS_TABLE)

        insertInitialCategories(db)
        insertInitialDetails(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        onCreate(db)
    }

    fun registerUser(email: String, fname: String, lname: String, username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_EMAIL, email)
            put(KEY_FIRST_NAME, fname)
            put(KEY_LAST_NAME, lname)
            put(KEY_USERNAME, username)
            put(KEY_PASSWORD, password)
        }

        val success = db.insert(TABLE_USERS, null, values)
        db.close()

        return success
    }

    fun loginUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$KEY_USERNAME = ? AND $KEY_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.moveToFirst() && cursor.count > 0
        cursor.close()

        return userExists
    }

    fun getUserInfo(username: String): User? {
        val db = this.readableDatabase
        val selection = "$KEY_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        val cursor: Cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null)

        return cursor.use {
            if (it.moveToFirst()) {
                val emailIndex = it.getColumnIndex(KEY_EMAIL)
                val firstNameIndex = it.getColumnIndex(KEY_FIRST_NAME)
                val lastNameIndex = it.getColumnIndex(KEY_LAST_NAME)
                val usernameIndex = it.getColumnIndex(KEY_USERNAME)
                val passwordIndex = it.getColumnIndex(KEY_PASSWORD)

                if (emailIndex != -1 && firstNameIndex != -1 && lastNameIndex != -1 &&
                    usernameIndex != -1 && passwordIndex != -1) {
                    return User(
                        it.getString(emailIndex),
                        it.getString(firstNameIndex),
                        it.getString(lastNameIndex),
                        it.getString(usernameIndex),
                        it.getString(passwordIndex)
                    )
                }
                else {
                    null
                }
            }
            else {
                null
            }
        }
    }
    private fun insertInitialCategories(db: SQLiteDatabase?) {
        val categories = listOf("Non-Fiction","Classics","Fantasy","Young Adult","Crime","Horror","Sci-Fi","Drama")

        categories.forEach { category ->
            val values = ContentValues().apply {
                put(KEY_CATEGORY_NAME, category)
            }

            db?.insert(TABLE_CATEGORIES, null, values)
        }
    }
    private fun insertInitialDetails(db: SQLiteDatabase?) {

        val book1 = ContentValues().apply {
            put(KEY_TITLE, "A Brief History of Humankind")
            put(KEY_AUTHOR, "Yuval Noah Harari")
            put(KEY_CATEGORY_ID_FK, getCategoryID("Non-Fiction", db)) //error pa siya wala adding of books muna nilagay ko
            put(KEY_RATING, "4.4/5")
            put(KEY_PRICE, "PHP 1,000")
            put(KEY_DESCRIPTION,"Sapiens: A Brief History of Humankind\" by Yuval Noah Harari offers a sweeping overview of human history, exploring the evolution of Homo sapiens from ancient times to the present. Harari covers vital milestones, including the Cognitive and Agricultural Revolutions, the formation of empires, and the impact of scientific advancements. The book prompts readers to reconsider established narratives, examining the interplay of biology and culture in shaping human societies. Through engaging storytelling, Harari presents a thought-provoking reflection on our species' past, present, and potential future.")
            put(KEY_IMAGE_SOURCE, "drawable/book1")
            put(KEY_ADD_TO_LIBRARY, 0)
            put(KEY_ADD_TO_FAVE, 0)
        }
        db?.insert(TABLE_DETAILS, null, book1)

        val book2 = ContentValues().apply {
            put(KEY_TITLE, "The Picture of Dorian Gray")
            put(KEY_AUTHOR, "Oscar Wilde")
            put(KEY_CATEGORY_ID_FK, getCategoryID("Classics", db)) //error pa siya wala adding of books muna nilagay ko
            put(KEY_RATING, "4.11/5")
            put(KEY_PRICE, "PHP 1,300")
            put(KEY_DESCRIPTION,"Oscar Wilde’s only novel is the dreamlike story of a young man who sells his soul for eternal youth and beauty. Wilde forged a devastating portrait of the effects of evil and debauchery on a young aesthete in late-19th-century England in this celebrated work. Combining elements of the Gothic horror novel and decadent French fiction, the book centers on a striking premise: As Dorian Gray sinks into a life of crime and gross sensuality, his body retains perfect youth and vigor while his recently painted portrait grows day by day into a hideous record of evil, which he must keep hidden from the world. This mesmerizing tale of horror and suspense has been popular for over a century. It ranks as one of Wilde's most important creations and among the classic achievements of its kind.")
            put(KEY_IMAGE_SOURCE, "drawable/book2")
            put(KEY_ADD_TO_LIBRARY, 0)
            put(KEY_ADD_TO_FAVE, 0)
        }
        db?.insert(TABLE_DETAILS, null, book2)

        val book3 = ContentValues().apply {
            put(KEY_TITLE, "The Name of the Wind")
            put(KEY_AUTHOR, "Patrick Rothfuss")
            put(KEY_CATEGORY_ID_FK, getCategoryID("Fantasy", db)) //error pa siya wala adding of books muna nilagay ko
            put(KEY_RATING, "4.55/5")
            put(KEY_PRICE, "PHP 1,055")
            put(KEY_DESCRIPTION,"\"The Name of the Wind\" follows the tale of Kvothe, a gifted young musician and magician. The story, narrated by Kvothe, recounts his journey from a gifted child in a traveling troupe to a powerful wizard, unraveling mysteries and facing formidable challenges. Rothfuss weaves a rich and immersive narrative, blending magic, music, and the intricate tapestry of Kvothe's life in a captivating fantasy world.")
            put(KEY_IMAGE_SOURCE, "drawable/book3")
            put(KEY_ADD_TO_LIBRARY, 0)
            put(KEY_ADD_TO_FAVE, 0)
        }
        db?.insert(TABLE_DETAILS, null, book3)

        val book4 = ContentValues().apply {
            put(KEY_TITLE, "The Hunger Games")
            put(KEY_AUTHOR, "Suzanne Collins")
            put(KEY_CATEGORY_ID_FK, getCategoryID("Young Adult", db)) //error pa siya wala adding of books muna nilagay ko
            put(KEY_RATING, "4.34/5")
            put(KEY_PRICE, "PHP 600")
            put(KEY_DESCRIPTION,"In a dystopian future, Katniss Everdeen volunteers to take her sister's place in the annual Hunger Games, a televised fight to the death. Suzanne Collins crafts a thrilling narrative that explores survival, sacrifice, and rebellion themes.")
            put(KEY_IMAGE_SOURCE, "drawable/book4")
            put(KEY_ADD_TO_LIBRARY, 0)
            put(KEY_ADD_TO_FAVE, 0)
        }
        db?.insert(TABLE_DETAILS, null, book4)

        val book5 = ContentValues().apply {
            put(KEY_TITLE, "The Girl with the Dragon Tattoo")
            put(KEY_AUTHOR, "Stieg Larsson")
            put(KEY_CATEGORY_ID_FK, getCategoryID("Crime", db)) //error pa siya wala adding of books muna nilagay ko
            put(KEY_RATING, "4.14/5")
            put(KEY_PRICE, "PHP 300")
            put(KEY_DESCRIPTION,"Mikael Blomkvist, a journalist, teams up with a brilliant hacker, Lisbeth Salander, to solve a decades-old disappearance in Sweden. Stieg Larsson's gripping novel combines crime, intrigue, and a complex investigation.")
            put(KEY_IMAGE_SOURCE, "drawable/book5")
            put(KEY_ADD_TO_LIBRARY, 0)
            put(KEY_ADD_TO_FAVE, 0)
        }
        db?.insert(TABLE_DETAILS, null, book5)

        val book6 = ContentValues().apply {
            put(KEY_TITLE, "The Shining")
            put(KEY_AUTHOR, "Stephen King")
            put(KEY_CATEGORY_ID_FK, getCategoryID("Horror", db)) //error pa siya wala adding of books muna nilagay ko
            put(KEY_RATING, "4.21/5")
            put(KEY_PRICE, "PHP 400")
            put(KEY_DESCRIPTION,"Stephen King's \"The Shining\" takes readers to the eerie Overlook Hotel, where the Torrance family faces supernatural forces. As the hotel exerts its malevolent influence, the story becomes a chilling exploration of isolation and madness.")
            put(KEY_IMAGE_SOURCE, "drawable/book6")
            put(KEY_ADD_TO_LIBRARY, 0)
            put(KEY_ADD_TO_FAVE, 0)
        }
        db?.insert(TABLE_DETAILS, null, book6)

        val book7 = ContentValues().apply {
            put(KEY_TITLE, "Dune")
            put(KEY_AUTHOR, "Frank Herbert")
            put(KEY_CATEGORY_ID_FK, getCategoryID("Sci-Fi", db)) //error pa siya wala adding of books muna nilagay ko
            put(KEY_RATING, "4.23/5")
            put(KEY_PRICE, "PHP 700")
            put(KEY_DESCRIPTION,"Frank Herbert's \"Dune\" is a science fiction epic set in a distant future. It follows young Paul Atreides as he navigates political intrigue, environmental challenges, and mystical forces on the desert planet of Arrakis.")
            put(KEY_IMAGE_SOURCE, "drawable/book7")
            put(KEY_ADD_TO_LIBRARY, 0)
            put(KEY_ADD_TO_FAVE, 0)
        }
        db?.insert(TABLE_DETAILS, null, book7)

        val book8 = ContentValues().apply {
            put(KEY_TITLE, "The Kite Runner")
            put(KEY_AUTHOR, "Khaled Hosseini")
            put(KEY_CATEGORY_ID_FK, getCategoryID("Drama", db)) //error pa siya wala adding of books muna nilagay ko
            put(KEY_RATING, "4.29/5")
            put(KEY_PRICE, "PHP 400")
            put(KEY_DESCRIPTION,"Khaled Hosseini's \"The Kite Runner\" explores friendship, betrayal, and redemption in war-torn Afghanistan. The novel follows the intertwined lives of Amir and Hassan against a backdrop of historical upheaval.")
            put(KEY_IMAGE_SOURCE, "drawable/book8")
            put(KEY_ADD_TO_LIBRARY, 0)
            put(KEY_ADD_TO_FAVE, 0)
        }
        db?.insert(TABLE_DETAILS, null, book8)
    }
    @SuppressLint("Range")
    private fun getCategoryID(categoryName: String, db: SQLiteDatabase?): Int {
        val selection = "$KEY_CATEGORY_NAME = ?"
        val selectionArgs = arrayOf(categoryName)
        val cursor = db?.query(TABLE_CATEGORIES, arrayOf(KEY_CATEGORY_ID), selection, selectionArgs, null, null, null)

        return cursor?.use {
            if (it.moveToFirst()) {
                it.getInt(it.getColumnIndex(KEY_CATEGORY_ID))
            } else {
                // If category not found, return a default value or handle appropriately
                -1
            }
        } ?: -1
    }
    @SuppressLint("Range")
    fun getBooksByCategory(category: String): List<Details> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_DETAILS WHERE $KEY_CATEGORY_ID_FK = ?"
        val selectionArgs = arrayOf(getCategoryID(category, db).toString())

        val cursor = db.rawQuery(query, selectionArgs)
        val books = mutableListOf<Details>()

        cursor.use {
            while (it.moveToNext()) {
                val book = Details(
                    it.getInt(it.getColumnIndex(KEY_DETAILS_ID)),
                    it.getString(it.getColumnIndex(KEY_TITLE)),
                    it.getString(it.getColumnIndex(KEY_AUTHOR)),
                    it.getString(it.getColumnIndex(KEY_CATEGORY_ID_FK)),
                    it.getString(it.getColumnIndex(KEY_IMAGE_SOURCE)),
                    it.getString(it.getColumnIndex(KEY_RATING)),
                    it.getString(it.getColumnIndex(KEY_PRICE)),
                    it.getString(it.getColumnIndex(KEY_DESCRIPTION)),
                    it.getInt(it.getColumnIndex(KEY_ADD_TO_LIBRARY)) !=0,
                    it.getInt(it.getColumnIndex(KEY_ADD_TO_FAVE)) !=0
                )
                books.add(book)
            }
        }

        db.close()
        return books
    }
}