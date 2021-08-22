# Graphs
Graphs Repository is an Android Plugin Development that helps to sort datasets in a graphical format.

# Live Preview


----------
Step 1 : Add to project root build.gradle.(Project Gradle)
----------
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
----------
Step 2 : Add the dependency.(Module Gradle)
----------
	dependencies {
	        implementation 'com.github.vsa15:Graphs:v1.0.0'
	}

----------
Step 3 : Add the XML Layout :art:
----------
	<com.valencio.lib.BarChartView
        android:id="@+id/barChartView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_centerInParent="true"
        android:padding="30dp"
        app:BarColor="#A58A54"
        app:BarRadius="6dp"
        app:BarTopTextBottomMargin="2dp"
        app:BarTopTextColor="#CE0E2D"
        app:BarTopTextSize="6dp"
        app:FormLineColor="#9B9B9B"
        app:XTextColor="#000000"
        app:XTextSize="8dp"
        app:XTextTopMargin="4dp"
        app:YTextColor="#000000"
        app:YTextRightMargin="4dp"
        app:YTextSize="8dp" />

----------
Step 4 : MainActivity.kt File (Initalize the View)💻 
----------
	val barChartView = findViewById<BarChartView>(R.id.barChartView)
        
----------
Step 5 :  Adding Live Data 👇
----------
	val list = arrayListOf<ChartBean>()
	list.add(ChartBean("Add the Particular Bar Title Text", BigDecimal(42000)))
----------
Step 6 : Combine Live Data to the View ⛔
----------
	barChartView.mData = list
----------
Simple Code Snippet: 
----------
	val list = arrayListOf<ChartBean>()
        val barChartView = findViewById<BarChartView>(R.id.barChartView)
        for (items in 0 until 2) {
            val value = random + items
            list.add(ChartBean("Loop Tasks ", BigDecimal(value)))
        }
        barChartView.mData = list
----------
