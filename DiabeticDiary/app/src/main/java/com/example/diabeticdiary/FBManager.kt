package com.example.diabeticdiary

import android.util.Log
//import com.google.firebase.Firebase

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class FBManager {
    private val database = Firebase.database("https://diabetic-diary-02242024-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var productList: List<Product> = emptyList()
    private var productListCallback: ((List<Product>) -> Unit)? = null
    private var mealList: List<Meal> = emptyList()
    private var mealListCallback: ((List<Meal>) -> Unit)? = null
    private var userList: User? = null
    private var userCallback: ((User?) -> Unit)? = null

    init {
        readUser { user ->
            userList = user
            userCallback?.invoke(userList)
        }

        readProducts { products ->
            productList = products
            productListCallback?.invoke(productList)
        }

        readMeals { meals ->
            mealList = meals
            mealListCallback?.invoke(mealList)
        }
    }

    fun readUser(callback: (User?) -> Unit) {
        val userData = database.getReference("user")
        userData.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                userList = user
                callback(userList)
                userCallback?.invoke(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("d", "Failed to read user value.", error.toException())
                callback(null)
            }
        })
    }

    fun setUserCallback(callback: (User?) -> Unit) {
        userCallback = callback
    }

    fun readProducts(callback: (List<Product>) -> Unit) {
        val productData = database.getReference("product")
        productData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val productList = mutableListOf<Product>()

                dataSnapshot.children.forEach { productSnapshot ->
                    // Convert each child node to a Product object
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }

                // Assign the fetched products to productList
                this@FBManager.productList = productList
                // Notify the callback if set
                callback(productList)
                // Notify the callback if set
                productListCallback?.invoke(productList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("d", "Failed to read value.", error.toException())
                // Pass an empty list to the callback function in case of failure
                callback(emptyList())
            }
        })
    }

    // Function to set a callback for accessing productList
    fun setProductListCallback(callback: (List<Product>) -> Unit) {
        productListCallback = callback
    }

    fun readMeals(callback: (List<Meal>) -> Unit) {
        val mealData = database.getReference("menu")
        mealData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mealList = mutableListOf<Meal>()

                dataSnapshot.children.forEach { mealSnapshot ->
                    // Convert each child node to a Product object
                    val meal = mealSnapshot.getValue(Meal::class.java)
                    if (meal != null) {
                        mealList.add(meal)
                    }
                }

                // Assign the fetched products to productList
                this@FBManager.mealList = mealList
                // Notify the callback if set
                callback(mealList)
                // Notify the callback if set
                mealListCallback?.invoke(mealList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("d", "Failed to read value.", error.toException())
                // Pass an empty list to the callback function in case of failure
                callback(emptyList())
            }
        })
    }

    // Function to set a callback for accessing productList
    fun setMealListCallback(callback: (List<Meal>) -> Unit) {
        mealListCallback = callback
    }

    fun writeProduct(name: String, nutrition: String) {
        val newProduct = Product(name, nutrition)
        val dataMap = mapOf(
            "name" to newProduct.name,
            "nutrition" to newProduct.nutrition
        )

        val ref = database.reference.child("product").push() // Generate a unique key for the child node
        ref.setValue(dataMap)
            .addOnSuccessListener {
                println("User data written to Firebase with auto-generated ID: ${ref.key}")
            }
            .addOnFailureListener { e ->
                println("Error writing user data to Firebase: $e")
            }
    }

    fun writeMeal(type: String, meal: String) {
        val newMeal = Meal(type, meal)
        val dataMap = mapOf(
            "type" to newMeal.type,
            "meal" to newMeal.meal
        )

        val ref = database.reference.child("menu").push() // Generate a unique key for the child node
        ref.setValue(dataMap)
            .addOnSuccessListener {
                println("User data written to Firebase with auto-generated ID: ${ref.key}")
            }
            .addOnFailureListener { e ->
                println("Error writing user data to Firebase: $e")
            }
    }




}