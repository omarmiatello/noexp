package com.github.omarmiatello.noexp

import com.github.omarmiatello.noexp.NoExpDBModel.*

object SampleDb {
    val noExp: NoExpDB = NoExpDB(
        archive = mapOf(
            "75041670" to mapOf(
                "5E" to ArchivedDao(
                    archiveDate = 1592733302931,
                    barcode = "75041670",
                    expireDate = 1599818050097,
                    expireDateType = null,
                    insertDate = 1592733302930,
                    qr = "5E",
                )
            ),
            "80035435" to mapOf(
                "9C" to ArchivedDao(
                    archiveDate = 1594067763281,
                    barcode = "80035435",
                    expireDate = 1634065847859,
                    expireDateType = null,
                    insertDate = 1584994253222,
                    qr = "9C",
                )
            ),
            "80042556" to mapOf(
                "96" to ArchivedDao(
                    archiveDate = 1595587142683,
                    barcode = "80042556",
                    expireDate = 1661972632589,
                    expireDateType = null,
                    insertDate = 1584993907610,
                    qr = "96",
                ),
                "98" to ArchivedDao(
                    archiveDate = 1585935497046,
                    barcode = "80042556",
                    expireDate = 1661972632589,
                    expireDateType = null,
                    insertDate = 1585935497045,
                    qr = "98",
                ),
                "99" to ArchivedDao(
                    archiveDate = 1597257605699,
                    barcode = "80042556",
                    expireDate = 1661972632589,
                    expireDateType = null,
                    insertDate = 1584993951826,
                    qr = "99",
                ),
                "CH" to ArchivedDao(
                    archiveDate = 1590492462452,
                    barcode = "80042556",
                    expireDate = 1661966781062,
                    expireDateType = null,
                    insertDate = 1590492462451,
                    qr = "CH",
                ),
                "CJ" to ArchivedDao(
                    archiveDate = 1589110479200,
                    barcode = "80042556",
                    expireDate = 1661966766381,
                    expireDateType = null,
                    insertDate = 1589110479200,
                    qr = "CJ",
                )
            ),
            "80053835" to mapOf(
                "BX" to ArchivedDao(
                    archiveDate = 1589388965653,
                    barcode = "80053835",
                    expireDate = 1605464500884,
                    expireDateType = null,
                    insertDate = 1589388965652,
                    qr = "BX",
                )
            ),
            "80129943" to mapOf(
                "K0" to ArchivedDao(
                    archiveDate = 1591456750146,
                    barcode = "80129943",
                    expireDate = 1592053932288,
                    expireDateType = null,
                    insertDate = 1591276634017,
                    qr = "K0",
                ),
                "LI" to ArchivedDao(
                    archiveDate = 1592948806079,
                    barcode = "80129943",
                    expireDate = 1592835706530,
                    expireDateType = null,
                    insertDate = 1592317621427,
                    qr = "LI",
                )
            )
        ),
        barcode = mapOf(
            "201738" to BarcodeDao(
                barcode = "201738",
                name = "salmone marinato in salsa pepe rosa",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F2017380004977.jpg?alt=media&token=13dcc126-f5b2-4c90-b537-736f4fd80195",
            ),
            "201740" to BarcodeDao(
                barcode = "201740",
                name = "orata marinata",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F2017400003935.jpg?alt=media&token=b03db716-86c1-4a4f-b85e-21b225f5f005",
            ),
            "201994" to BarcodeDao(
                barcode = "201994",
                name = "costine di maiale",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F2019940008128.jpg?alt=media&token=bfe5e5df-9845-4a85-9e52-b0db3624bc4f",
            ),
            "202736" to BarcodeDao(
                barcode = "202736",
                name = "formaggio Camoscio d'Oro",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F2027360002473.jpg?alt=media&token=4efe040e-47c9-49e0-aa47-557f931dac09",
            ),
            "202970" to BarcodeDao(
                barcode = "202970",
                name = "brie Esselunga",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F2029700002105.jpg?alt=media&token=ab7ae27a-eafd-4848-87da-6b4dec729f8d",
            )
        ),
        cart = mapOf(
            "-MI-C7cE2F62C6TIrNBJ" to ProductCartDao(
                barcode = "8009245003007",
                cat = listOf(
                    "Acqua frizzante"
                ),
                catParents = null,
                expireDate = 1629032496714,
                expireDateType = "R",
                id = "-MI-C7cE2F62C6TIrNBJ",
                insertDate = 1600952501253,
                name = "acqua frizzante Brio Blu",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F8009245003007.jpg?alt=media&token=cca41ee6-b54b-4189-8682-9720d55ffd3d",
            ),
            "-MI-C9Z-w7xLOZ-toAVn" to ProductCartDao(
                barcode = "8009245003007",
                cat = listOf(
                    "Acqua frizzante"
                ),
                catParents = null,
                expireDate = 1629032496714,
                expireDateType = "R",
                id = "-MI-C9Z-w7xLOZ-toAVn",
                insertDate = 1600952509176,
                name = "acqua frizzante Brio Blu",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F8009245003007.jpg?alt=media&token=cca41ee6-b54b-4189-8682-9720d55ffd3d",
            ),
            "-MI-CXnEVuMwis4EWQF2" to ProductCartDao(
                barcode = "6000290411245",
                cat = listOf(
                    "Acqua naturale"
                ),
                catParents = null,
                expireDate = 1657890096714,
                expireDateType = "R",
                id = "-MI-CXnEVuMwis4EWQF2",
                insertDate = 1600952608455,
                name = "acqua naturale Boario",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F6000290411245.jpg?alt=media&token=d2a1d55d-74af-43c4-afb1-cb1de1e957b0",
            ),
            "-MI-CqHstSYQozNypfl-" to ProductCartDao(
                barcode = "8030582900780",
                cat = listOf(
                    "Latte"
                ),
                catParents = null,
                expireDate = 1608645696714,
                expireDateType = "R",
                id = "-MI-CqHstSYQozNypfl-",
                insertDate = 1600952688303,
                name = "latte intero a lunga conservazione",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F8030582900780.jpg?alt=media&token=ede435fd-1c35-4f94-b4fb-fd84c4545e35",
            )
        ),
        category = mapOf(
            "Alimenti" to CategoryDao(
                alias = null,
                allParents = null,
                desired = null,
                directChildren = listOf(
                    "Alimenti Bimbi",
                    "Carne",
                    "Condimento",
                    "Dolci e Snack",
                    "Frutta e Verdura"
                ),
                directParent = null,
                expireDays = 306,
                max = null,
                maxPerWeek = 20,
                maxPerYear = null,
                min = null,
                name = "Alimenti",
            ),
            "Alimenti Bimbi" to CategoryDao(
                alias = null,
                allParents = listOf(
                    "Alimenti"
                ),
                desired = null,
                directChildren = null,
                directParent = "Alimenti",
                expireDays = null,
                max = null,
                maxPerWeek = null,
                maxPerYear = null,
                min = null,
                name = "Alimenti Bimbi",
            ),
            "Carne" to CategoryDao(
                alias = null,
                allParents = listOf(
                    "Alimenti"
                ),
                desired = null,
                directChildren = listOf(
                    "Agnello",
                    "Carne macinata",
                    "Cotolette",
                    "Grigliata mista",
                    "Hamburger"
                ),
                directParent = "Alimenti",
                expireDays = 58,
                max = null,
                maxPerWeek = 3,
                maxPerYear = null,
                min = null,
                name = "Carne",
            ),
            "Agnello" to CategoryDao(
                alias = null,
                allParents = listOf(
                    "Alimenti",
                    "Carne"
                ),
                desired = null,
                directChildren = null,
                directParent = "Carne",
                expireDays = null,
                max = null,
                maxPerWeek = 1,
                maxPerYear = null,
                min = null,
                name = "Agnello",
            ),
            "Carne macinata" to CategoryDao(
                alias = null,
                allParents = listOf(
                    "Alimenti",
                    "Carne"
                ),
                desired = null,
                directChildren = null,
                directParent = "Carne",
                expireDays = null,
                max = null,
                maxPerWeek = null,
                maxPerYear = null,
                min = null,
                name = "Carne macinata",
            )
        ),
        expireDateByBarcode = mapOf(
            "8714100812624" to 269,
            "8002330006877" to 859,
            "8017139101685" to 1268,
            "8012386360328" to 312,
            "8003315210500" to 305
        ),
        home = mapOf(
            "08" to ProductDao(
                barcode = "8714100812624",
                cat = listOf(
                    "Passato",
                    "Carote",
                    "Porri"
                ),
                catParents = listOf(
                    "Alimenti",
                    "Condimento",
                    "Frutta e Verdura",
                    "Verdura"
                ),
                expireDate = 1602777915014,
                expireDateType = null,
                insertDate = 1579453516627,
                lastCheckDate = 1599860646073,
                name = "passato di carote e porri",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F8714100812624.jpg?alt=media&token=86acaa92-b4b2-4f13-aaae-072245e61633",
                position = "dispensa cassetto 3",
                qr = "08",
            ),
            "20" to ProductDao(
                barcode = "8002330006877",
                cat = listOf(
                    "Ananas allo sciroppo",
                    "Ananas"
                ),
                catParents = listOf(
                    "Bevande",
                    "Sciroppo",
                    "Alimenti",
                    "Frutta e Verdura",
                    "Frutta"
                ),
                expireDate = 1654018802785,
                expireDateType = null,
                insertDate = 1579718463370,
                lastCheckDate = 1599860042143,
                name = "ananas allo sciroppo",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F8002330006877.jpg?alt=media&token=af1c174f-eb08-4ca7-a68c-096b0064356d",
                position = "dispensa cassetto 2",
                qr = "20",
            ),
            "22" to ProductDao(
                barcode = "8017139101685",
                cat = listOf(
                    "Marmellata",
                    "Arance"
                ),
                catParents = listOf(
                    "Alimenti",
                    "Dolci e Snack",
                    "Dolci",
                    "Frutta e Verdura",
                    "Frutta"
                ),
                expireDate = 1689356581720,
                expireDateType = null,
                insertDate = 1579718584761,
                lastCheckDate = 1599860070143,
                name = "marmellata di arancia rossa di Sicilia",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F8017139101685.jpg?alt=media&token=02fca5e1-2b21-4be2-afd6-98f577b667fe",
                position = "dispensa cassetto 2",
                qr = "22",
            ),
            "24" to ProductDao(
                barcode = "8012386360328",
                cat = listOf(
                    "Arachidi salate"
                ),
                catParents = listOf(
                    "Alimenti",
                    "Dolci e Snack",
                    "Snack",
                    "Arachidi"
                ),
                expireDate = 1606761904582,
                expireDateType = null,
                insertDate = 1579718708339,
                lastCheckDate = 1599861106738,
                name = "arachidi salate",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F8012386360328.jpg?alt=media&token=53aacd81-c2c4-4773-b84d-0eb53f1d40cb",
                position = "dispensa cassetto 6",
                qr = "24",
            ),
            "31" to ProductDao(
                barcode = "8003315210500",
                cat = listOf(
                    "Funghi porcini",
                    "Porcini"
                ),
                catParents = listOf(
                    "Alimenti",
                    "Frutta e Verdura",
                    "Verdura",
                    "Funghi"
                ),
                expireDate = 1606583718509,
                expireDateType = null,
                insertDate = 1580145325686,
                lastCheckDate = 1599860484865,
                name = "funghi porcini secchi",
                pictureUrl = "https://firebasestorage.googleapis.com/v0/b/noexp-for-home.appspot.com/o/barcode%2F8003315210500.jpg?alt=media&token=0b444b77-9330-4a4a-b22d-d1bd1968d8db",
                position = "dispensa cassetto 3",
                qr = "31",
            )
        ),
        lastQr = "SI",
    )
}