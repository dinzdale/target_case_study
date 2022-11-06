package com.target.targetcasestudy.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColor
import com.target.targetcasestudy.network.DealsViewModel
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.ItemNotFoundResponse
import com.target.targetcasestudy.model.Product
import com.target.targetcasestudy.model.getErrorResponse
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductDetailsFragment : Fragment() {

    val dealsViewModel: DealsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_details, container, false)
        return ComposeView(requireContext()).apply {
            setContent {

                MaterialTheme {
                    Surface() {
                        BuildUI()
                    }
                }
            }
        }


    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun BuildUI() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(scaffoldState = scaffoldState, topBar = { TopBar() }) {
            Box(Modifier
                .fillMaxSize()
                .background(Color.LightGray), contentAlignment = Alignment.TopCenter) {
                ShowProductDetails() {
                    it.getErrorResponse(404, ItemNotFoundResponse::class.java)?.also {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                "${it.code}: ${it.message}")
                        }
                    } ?: apply {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                requireContext().getString(R.string.generic_error))
                        }
                    }
                }
            }
            LaunchedEffect(true) {
                dealsViewModel.retrieveDeal(dealsViewModel.selecedDealId)
            }
        }
    }

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    fun TopBar() {
        TopAppBar() {
            Row(Modifier
                .background(Color.White)
                .fillMaxSize()
                .clickable {
                    findNavController().popBackStack()
                }, horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.ic_left_arrow_thin),
                    contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier
                        .size(20.dp)
                        .padding(start = 8.dp))
                Text(stringResource(id = R.string.details), color = Color.Black,
                    modifier = Modifier.padding(start = 50.dp), fontWeight = FontWeight(700),
                    fontSize = TextUnit(18f, TextUnitType.Sp))
            }
        }
    }

    @Composable
    fun ShowProductDetails(result: State<Result<Product>?> = dealsViewModel.productState,
        onException: (Exception) -> Unit) {
        val scrollState = rememberScrollState()
        result.value?.apply {
            onSuccess {
                ConstraintLayout(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)) {
                    val (products, cartBtn) = createRefs()
                    Column(verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .constrainAs(products) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                            .verticalScroll(scrollState)
                            .fillMaxSize()) {
                        ProductCard(product = it)
                        ProductDescriptionCard(product = it)
                    }
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .constrainAs(cartBtn) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }, onClick = {}, colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(
                        id = R.color.colorPrimary))) {
                        Text(stringResource(id = R.string.add_to_cart),color = colorResource(id = R.color.white))
                    }
                }
            }
            onFailure {
                onException(it as Exception)
            }
        }

    }

    @OptIn(ExperimentalGlideComposeApi::class, ExperimentalUnitApi::class)
    @Composable
    fun ProductCard(product: Product) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            Column(modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                GlideImage(modifier = Modifier.fillMaxWidth(), model = product.imageUrl,
                    contentDescription = null) {
                    it.fallback(R.drawable.ic_launcher_foreground)
                }
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), text = product.title, textAlign = TextAlign.Start,
                    fontSize = TextUnit(18f, TextUnitType.Sp), fontWeight = FontWeight(400))
                Row(Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp), verticalAlignment = Alignment.Bottom) {
                    product.salePrice?.displayString?.also {
                        Text(text = it, color = colorResource(id = R.color.colorPrimary),
                            fontWeight = FontWeight.Bold, fontSize = TextUnit(20f, TextUnitType.Sp))
                        product.regularPrice.displayString?.also {
                            Text(text = requireContext().getString(R.string.reg_price, it),
                                color = Color.Black,
                                fontSize = TextUnit(14f, TextUnitType.Sp))
                        }
                    } ?: apply {
                        product.regularPrice.displayString?.also {
                            Text(text = it,
                                color = Color.Black,
                                fontSize = TextUnit(14f, TextUnitType.Sp))
                        }
                    }
                }
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), text = product.fulfillment, textAlign = TextAlign.Start)
            }
        }
    }

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    fun ProductDescriptionCard(product: Product) {
        Card(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.product_details),
                    modifier = Modifier.padding(vertical = 10.dp),
                    color = Color.Black,
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    fontWeight = FontWeight(700),
                )

                Text(text = product.description,
                    color = Color.Gray,
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    fontWeight = FontWeight(400), maxLines = 18, overflow = TextOverflow.Ellipsis)
            }
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        super.onAttach(context)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}