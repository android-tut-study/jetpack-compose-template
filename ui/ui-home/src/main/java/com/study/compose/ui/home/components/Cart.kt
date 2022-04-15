package com.study.compose.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.data.Cart
import com.study.compose.ui.home.data.SampleCartItems

@Composable
fun BottomCart(modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Row(modifier = modifier) {
        Surface(color = MaterialTheme.colors.secondary, shape = MaterialTheme.shapes.large) {
            if (expanded) {
                ExpandedCarts {
                    expanded = false
                }
            } else {
                CollapsedCart {
                    expanded = true
                }
            }
        }
    }
}


@Composable
fun CollapsedCart(
    carts: List<Cart> = SampleCartItems.subList(0, 2),
    onTap: () -> Unit
) {
    Row(
        Modifier
            .clickable { onTap() }
            .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Shopping Cart")
        }
        carts.forEach {
            CollapseCartItem(it)
        }
    }
}

@Composable
fun CollapseCartItem(cart: Cart) {
    Image(
        painter = painterResource(id = cart.photoResId),
        contentDescription = "Collapse Cart Item",
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

@Composable
fun CartHeader(cartSize: Int, onTap: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onTap() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Arrow Down"
            )
        }

        Text(text = "Cart".uppercase(), style = MaterialTheme.typography.subtitle1)

        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "$cartSize items".uppercase())
    }
}

@Composable
fun ExpandedCarts(carts: List<Cart> = SampleCartItems, onCollapse: () -> Unit) {

    Surface(color = MaterialTheme.colors.secondary) {
        Column(Modifier.fillMaxSize()) {
            CartHeader(carts.size) {
                onCollapse()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp)
            ) {
                carts.forEach {
                    CartItem(cart = it)
                }
            }
        }
    }

}

@Composable
fun CartItem(
    cart: Cart
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { /*TODO*/ }, Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.RemoveCircleOutline,
                contentDescription = "Remove Cart"
            )
        }
        Column(Modifier.fillMaxWidth()) {
            Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
            Row(
                modifier = Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = cart.photoResId),
                    contentDescription = "Image "
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(Modifier.padding(end = 16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = cart.vendor.name)
                        Text(text = "$${cart.price}")
                    }
                    Text(text = cart.title)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.secondary) {
            CartItem(cart = SampleCartItems[0])
        }
    }
}

@Preview
@Composable
fun CollapsedCartPreview() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.secondary) {
            CollapsedCart {

            }
        }
    }
}

@Preview
@Composable
fun CartHeaderPreview() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.secondary) {
            CartHeader(5) {

            }
        }
    }
}


@Preview
@Composable
fun ExpandedCartItemsPreview() {
    ShrineComposeTheme {
        ExpandedCarts {

        }
    }
}
