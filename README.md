# Graphs
Graphs Repository is an Android Plugin Development that helps to sort datasets in a graphical format.

# Live Preview

https://user-images.githubusercontent.com/46339440/150670480-da9510b4-589a-4bba-8633-87d6b4ebcc15.mp4

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

Bar Chart : 

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

Pie Chart : 

	<com.valencio.lib.PieChart
            android:id="@+id/pieChartView"
            android:layout_width="match_parent"
            android:layout_height="300dp"
            android:layout_centerInParent="true"
            android:padding="45dp"
            app:PieRadius="100dp"
            app:PieTextColor="#000000"
            app:PieTextSize="8dp"
            app:TextSize="10dp"
            app:TextTopMargin="6dp" />

----------
Step 4 : MainActivity.kt File (Initalize the View)💻 
----------

Bar Chart : 

	val barChartView = findViewById<BarChartView>(R.id.barChartView)
	
Pie Chart : 

	val pieChartView = findViewById<PieChart>(R.id.pieChartView)
        
----------
Step 5 :  Adding Live Data 👇
----------

Bar Chart : 

	val list = arrayListOf<ChartBean>()
	list.add(ChartBean("Add the Particular Bar Title Text", BigDecimal(42000)))
	
Pie Chart : 

	val list = arrayListOf<PieChartBean>()
        list.add(PieChartBean("Add the Particular Bar Title Text", BigDecimal(30), Color.BLUE))

----------
Step 6 : Combine Live Data to the View :heavy_plus_sign:
----------

Bar Chart : 

	barChartView.mData = list
	
Pie Chart : 

	pieChartView!!.mData = list
	
----------
Code Snippet: (Use Case) 
----------

Bar Chart : 

	val list = arrayListOf<ChartBean>()
        val barChartView = findViewById<BarChartView>(R.id.barChartView)
        for (items in 0 until 2) {
            val value = random + items
            list.add(ChartBean("Loop Tasks ", BigDecimal(value)))
        }
        barChartView.mData = list

Pie Chart : 

	val pieChartView = findViewById<PieChart>(R.id.pieChartView)
        val list = arrayListOf<PieChartBean>()
        list.add(PieChartBean("Add the Particular  Title Text", BigDecimal(15), Color.CYAN))
        list.add(PieChartBean("Add the Particular Title Text", BigDecimal(7), Color.MAGENTA))
        pieChartView!!.mData = list
----------
