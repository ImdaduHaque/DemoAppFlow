package com.imdadul.appdemo.ui.activity

import android.animation.ArgbEvaluator
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.imdadul.appdemo.R
import android.support.design.widget.CoordinatorLayout
import android.widget.ImageButton
import android.support.v4.view.ViewPager
import android.support.v4.content.ContextCompat
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.*
import com.imdadul.appdemo.utils.Utils
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater




class PagerActivity : AppCompatActivity() {

    var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var mViewPager: ViewPager? = null
    var mNextBtn: ImageButton? = null
    var mSkipBtn: Button? = null
    var mFinishBtn:Button? = null

    var zero: ImageView? = null
    var one:ImageView? = null
    var two:ImageView? = null
    var indicators: Array<ImageView>? = null

    var lastLeftValue = 0

    var mCoordinator: CoordinatorLayout? = null


    val TAG = "PagerActivity"

    var page = 0   //  to track page position

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = ContextCompat.getColor(this, R.color.blacktransparent)
        }*/
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pager)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        mNextBtn = findViewById(R.id.intro_btn_next) as ImageButton
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            mNextBtn!!.setImageDrawable(
                    Utils.tintMyDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_24dp), Color.WHITE)
            )

        mSkipBtn = findViewById(R.id.intro_btn_skip) as Button
        mFinishBtn = findViewById(R.id.intro_btn_finish) as Button

        zero = findViewById(R.id.intro_indicator_0) as ImageView
        one = findViewById(R.id.intro_indicator_1) as ImageView
        two = findViewById(R.id.intro_indicator_2) as ImageView

        mCoordinator = findViewById(R.id.main_content) as CoordinatorLayout


        indicators = arrayOf<ImageView>(zero!!, one!!, two!!)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById<View>(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        mViewPager!!.currentItem = page
        updateIndicators(page)

        val color1 = ContextCompat.getColor(this, R.color.colorCyan)
        val color2 = ContextCompat.getColor(this, R.color.orange)
        val color3 = ContextCompat.getColor(this, R.color.green_color)

        val colorList = intArrayOf(color1, color2, color3)

        val evaluator = ArgbEvaluator()

        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                /*
                color update
                 */
                val colorUpdate = evaluator.evaluate(positionOffset, colorList[position], colorList[if (position == 2) position else position + 1]) as Int
                mViewPager!!.setBackgroundColor(colorUpdate)

            }

            override fun onPageSelected(position: Int) {

                page = position

                updateIndicators(page)

                when (position) {
                    0 -> mViewPager!!.setBackgroundColor(color1)
                    1 -> mViewPager!!.setBackgroundColor(color2)
                    2 -> mViewPager!!.setBackgroundColor(color3)
                }

                mNextBtn!!.setVisibility(if (position == 2) View.GONE else View.VISIBLE)
                mFinishBtn!!.setVisibility(if (position == 2) View.VISIBLE else View.GONE)

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        });

        mNextBtn!!.setOnClickListener(View.OnClickListener {
            page += 1
            mViewPager!!.setCurrentItem(page, true)
        })

        mSkipBtn!!.setOnClickListener(View.OnClickListener { finish() })

        mFinishBtn!!.setOnClickListener(View.OnClickListener {
            finish()
            //  update 1st time pref
            //Utils.saveSharedSetting(this@PagerActivity, MainActivity.PREF_USER_FIRST_TIME, "false")

            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        })
    }

    fun updateIndicators(position: Int) {
        for (i in 0 until indicators!!.size) {
            indicators!![i].setBackgroundResource(
                    if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_pager, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }


    /**
     * A placeholder fragment containing a simple view.
     */


    class PlaceholderFragment : Fragment() {
        var img: ImageView? = null

        internal var bgs = intArrayOf(R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp)

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_pager, container, false)
            val textView = rootView.findViewById(R.id.section_label) as TextView
            textView.text = getString(R.string.section_format, arguments!!.getInt(ARG_SECTION_NUMBER))

            img = rootView.findViewById(R.id.section_img) as ImageView
            img!!.setBackgroundResource(bgs[arguments!!.getInt(ARG_SECTION_NUMBER) - 1])


            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)

        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "SECTION 1"
                1 -> return "SECTION 2"
                2 -> return "SECTION 3"
            }
            return null
        }

    }
}
