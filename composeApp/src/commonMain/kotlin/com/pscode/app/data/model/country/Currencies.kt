package com.pscode.app.data.model.country

import kotlinx.serialization.Serializable

@Serializable
data class Currencies(
    val AED: AED = AED(),
    val AFN: AFN = AFN(),
    val ALL: ALL = ALL(),
    val AMD: AMD = AMD(),
    val ANG: ANG = ANG(),
    val AOA: AOA = AOA(),
    val ARS: ARS = ARS(),
    val AUD: AUD = AUD(),
    val AWG: AWG = AWG(),
    val AZN: AZN = AZN(),
    val BAM: BAM = BAM(),
    val BBD: BBD = BBD(),
    val BDT: BDT = BDT(),
    val BGN: BGN = BGN(),
    val BHD: BHD = BHD(),
    val BIF: BIF = BIF(),
    val BMD: BMD = BMD(),
    val BND: BND = BND(),
    val BOB: BOB = BOB(),
    val BRL: BRL = BRL(),
    val BSD: BSD = BSD(),
    val BTN: BTN = BTN(),
    val BWP: BWP = BWP(),
    val BYN: BYN = BYN(),
    val BZD: BZD = BZD(),
    val CAD: CAD = CAD(),
    val CDF: CDF = CDF(),
    val CHF: CHF = CHF(),
    val CKD: CKD = CKD(),
    val CLP: CLP = CLP(),
    val CNY: CNY = CNY(),
    val COP: COP = COP(),
    val CRC: CRC = CRC(),
    val CUC: CUC = CUC(),
    val CUP: CUP = CUP(),
    val CVE: CVE = CVE(),
    val CZK: CZK = CZK(),
    val DJF: DJF = DJF(),
    val DKK: DKK = DKK(),
    val DOP: DOP = DOP(),
    val DZD: DZD = DZD(),
    val EGP: EGP = EGP(),
    val ERN: ERN = ERN(),
    val ETB: ETB = ETB(),
    val EUR: EUR = EUR(),
    val FJD: FJD = FJD(),
    val FKP: FKP = FKP(),
    val FOK: FOK = FOK(),
    val GBP: GBP = GBP(),
    val GEL: GEL = GEL(),
    val GGP: GGP = GGP(),
    val GHS: GHS = GHS(),
    val GIP: GIP = GIP(),
    val GMD: GMD = GMD(),
    val GNF: GNF = GNF(),
    val GTQ: GTQ = GTQ(),
    val GYD: GYD = GYD(),
    val HKD: HKD = HKD(),
    val HNL: HNL = HNL(),
    val HTG: HTG = HTG(),
    val HUF: HUF = HUF(),
    val IDR: IDR = IDR(),
    val ILS: ILS = ILS(),
    val IMP: IMP = IMP(),
    val INR: INR = INR(),
    val IQD: IQD = IQD(),
    val IRR: IRR = IRR(),
    val ISK: ISK = ISK(),
    val JEP: JEP = JEP(),
    val JMD: JMD = JMD(),
    val JOD: JOD = JOD(),
    val JPY: JPY = JPY(),
    val KES: KES = KES(),
    val KGS: KGS = KGS(),
    val KHR: KHR = KHR(),
    val KID: KID = KID(),
    val KMF: KMF = KMF(),
    val KPW: KPW = KPW(),
    val KRW: KRW = KRW(),
    val KWD: KWD = KWD(),
    val KYD: KYD = KYD(),
    val KZT: KZT = KZT(),
    val LAK: LAK = LAK(),
    val LBP: LBP = LBP(),
    val LKR: LKR = LKR(),
    val LRD: LRD = LRD(),
    val LSL: LSL = LSL(),
    val LYD: LYD = LYD(),
    val MAD: MAD = MAD(),
    val MDL: MDL = MDL(),
    val MGA: MGA = MGA(),
    val MKD: MKD = MKD(),
    val MMK: MMK = MMK(),
    val MNT: MNT = MNT(),
    val MOP: MOP = MOP(),
    val MRU: MRU = MRU(),
    val MUR: MUR = MUR(),
    val MVR: MVR = MVR(),
    val MWK: MWK = MWK(),
    val MXN: MXN = MXN(),
    val MYR: MYR = MYR(),
    val MZN: MZN = MZN(),
    val NAD: NAD = NAD(),
    val NGN: NGN = NGN(),
    val NIO: NIO = NIO(),
    val NOK: NOK = NOK(),
    val NPR: NPR = NPR(),
    val NZD: NZD = NZD(),
    val OMR: OMR = OMR(),
    val PAB: PAB = PAB(),
    val PEN: PEN = PEN(),
    val PGK: PGK = PGK(),
    val PHP: PHP = PHP(),
    val PKR: PKR = PKR(),
    val PLN: PLN = PLN(),
    val PYG: PYG = PYG(),
    val QAR: QAR = QAR(),
    val RON: RON = RON(),
    val RSD: RSD = RSD(),
    val RUB: RUB = RUB(),
    val RWF: RWF = RWF(),
    val SAR: SAR = SAR(),
    val SBD: SBD = SBD(),
    val SCR: SCR = SCR(),
    val SDG: SDG = SDG(),
    val SEK: SEK = SEK(),
    val SGD: SGD = SGD(),
    val SHP: SHP = SHP(),
    val SLL: SLL = SLL(),
    val SOS: SOS = SOS(),
    val SRD: SRD = SRD(),
    val SSP: SSP = SSP(),
    val STN: STN = STN(),
    val SYP: SYP = SYP(),
    val SZL: SZL = SZL(),
    val THB: THB = THB(),
    val TJS: TJS = TJS(),
    val TMT: TMT = TMT(),
    val TND: TND = TND(),
    val TOP: TOP = TOP(),
    val TRY: TRY = TRY(),
    val TTD: TTD = TTD(),
    val TVD: TVD = TVD(),
    val TWD: TWD = TWD(),
    val TZS: TZS = TZS(),
    val UAH: UAH = UAH(),
    val UGX: UGX = UGX(),
    val USD: USD = USD(),
    val UYU: UYU = UYU(),
    val UZS: UZS = UZS(),
    val VES: VES = VES(),
    val VND: VND = VND(),
    val VUV: VUV = VUV(),
    val WST: WST = WST(),
    val XAF: XAF = XAF(),
    val XCD: XCD = XCD(),
    val XOF: XOF = XOF(),
    val XPF: XPF = XPF(),
    val YER: YER = YER(),
    val ZAR: ZAR = ZAR(),
    val ZMW: ZMW = ZMW(),
    val ZWL: ZWL = ZWL()
) {

    fun toNonEmptyCurrencyNamesList(): List<String> {
        val currencyNamesList = mutableListOf<String>()

        if (AED.name.isNotBlank()) currencyNamesList.add(AED.name)
        if (AFN.name.isNotBlank()) currencyNamesList.add(AFN.name)
        if (ALL.name.isNotBlank()) currencyNamesList.add(ALL.name)
        if (AMD.name.isNotBlank()) currencyNamesList.add(AMD.name)
        if (ANG.name.isNotBlank()) currencyNamesList.add(ANG.name)
        if (AOA.name.isNotBlank()) currencyNamesList.add(AOA.name)
        if (ARS.name.isNotBlank()) currencyNamesList.add(ARS.name)
        if (AUD.name.isNotBlank()) currencyNamesList.add(AUD.name)
        if (AWG.name.isNotBlank()) currencyNamesList.add(AWG.name)
        if (AZN.name.isNotBlank()) currencyNamesList.add(AZN.name)
        if (BAM.name.isNotBlank()) currencyNamesList.add(BAM.name)
        if (BBD.name.isNotBlank()) currencyNamesList.add(BBD.name)
        if (BDT.name.isNotBlank()) currencyNamesList.add(BDT.name)
        if (BGN.name.isNotBlank()) currencyNamesList.add(BGN.name)
        if (BHD.name.isNotBlank()) currencyNamesList.add(BHD.name)
        if (BIF.name.isNotBlank()) currencyNamesList.add(BIF.name)
        if (BMD.name.isNotBlank()) currencyNamesList.add(BMD.name)
        if (BND.name.isNotBlank()) currencyNamesList.add(BND.name)
        if (BOB.name.isNotBlank()) currencyNamesList.add(BOB.name)
        if (BRL.name.isNotBlank()) currencyNamesList.add(BRL.name)
        if (BSD.name.isNotBlank()) currencyNamesList.add(BSD.name)
        if (BTN.name.isNotBlank()) currencyNamesList.add(BTN.name)
        if (BWP.name.isNotBlank()) currencyNamesList.add(BWP.name)
        if (BYN.name.isNotBlank()) currencyNamesList.add(BYN.name)
        if (BZD.name.isNotBlank()) currencyNamesList.add(BZD.name)
        if (CAD.name.isNotBlank()) currencyNamesList.add(CAD.name)
        if (CDF.name.isNotBlank()) currencyNamesList.add(CDF.name)
        if (CHF.name.isNotBlank()) currencyNamesList.add(CHF.name)
        if (CKD.name.isNotBlank()) currencyNamesList.add(CKD.name)
        if (CLP.name.isNotBlank()) currencyNamesList.add(CLP.name)
        if (CNY.name.isNotBlank()) currencyNamesList.add(CNY.name)
        if (COP.name.isNotBlank()) currencyNamesList.add(COP.name)
        if (CRC.name.isNotBlank()) currencyNamesList.add(CRC.name)
        if (CUC.name.isNotBlank()) currencyNamesList.add(CUC.name)
        if (CUP.name.isNotBlank()) currencyNamesList.add(CUP.name)
        if (CVE.name.isNotBlank()) currencyNamesList.add(CVE.name)
        if (CZK.name.isNotBlank()) currencyNamesList.add(CZK.name)
        if (DJF.name.isNotBlank()) currencyNamesList.add(DJF.name)
        if (DKK.name.isNotBlank()) currencyNamesList.add(DKK.name)
        if (DOP.name.isNotBlank()) currencyNamesList.add(DOP.name)
        if (DZD.name.isNotBlank()) currencyNamesList.add(DZD.name)
        if (EGP.name.isNotBlank()) currencyNamesList.add(EGP.name)
        if (ERN.name.isNotBlank()) currencyNamesList.add(ERN.name)
        if (ETB.name.isNotBlank()) currencyNamesList.add(ETB.name)
        if (EUR.name.isNotBlank()) currencyNamesList.add(EUR.name)
        if (FJD.name.isNotBlank()) currencyNamesList.add(FJD.name)
        if (FKP.name.isNotBlank()) currencyNamesList.add(FKP.name)
        if (FOK.name.isNotBlank()) currencyNamesList.add(FOK.name)
        if (GBP.name.isNotBlank()) currencyNamesList.add(GBP.name)
        if (GEL.name.isNotBlank()) currencyNamesList.add(GEL.name)
        if (GGP.name.isNotBlank()) currencyNamesList.add(GGP.name)
        if (GHS.name.isNotBlank()) currencyNamesList.add(GHS.name)
        if (GIP.name.isNotBlank()) currencyNamesList.add(GIP.name)
        if (GMD.name.isNotBlank()) currencyNamesList.add(GMD.name)
        if (GNF.name.isNotBlank()) currencyNamesList.add(GNF.name)
        if (GTQ.name.isNotBlank()) currencyNamesList.add(GTQ.name)
        if (GYD.name.isNotBlank()) currencyNamesList.add(GYD.name)
        if (HKD.name.isNotBlank()) currencyNamesList.add(HKD.name)
        if (HNL.name.isNotBlank()) currencyNamesList.add(HNL.name)
        if (HTG.name.isNotBlank()) currencyNamesList.add(HTG.name)
        if (HUF.name.isNotBlank()) currencyNamesList.add(HUF.name)
        if (IDR.name.isNotBlank()) currencyNamesList.add(IDR.name)
        if (ILS.name.isNotBlank()) currencyNamesList.add(ILS.name)
        if (IMP.name.isNotBlank()) currencyNamesList.add(IMP.name)
        if (INR.name.isNotBlank()) currencyNamesList.add(INR.name)
        if (IQD.name.isNotBlank()) currencyNamesList.add(IQD.name)
        if (IRR.name.isNotBlank()) currencyNamesList.add(IRR.name)
        if (ISK.name.isNotBlank()) currencyNamesList.add(ISK.name)
        if (JEP.name.isNotBlank()) currencyNamesList.add(JEP.name)
        if (JMD.name.isNotBlank()) currencyNamesList.add(JMD.name)
        if (JOD.name.isNotBlank()) currencyNamesList.add(JOD.name)
        if (JPY.name.isNotBlank()) currencyNamesList.add(JPY.name)
        if (KES.name.isNotBlank()) currencyNamesList.add(KES.name)
        if (KGS.name.isNotBlank()) currencyNamesList.add(KGS.name)
        if (KHR.name.isNotBlank()) currencyNamesList.add(KHR.name)
        if (KID.name.isNotBlank()) currencyNamesList.add(KID.name)
        if (KMF.name.isNotBlank()) currencyNamesList.add(KMF.name)
        if (KPW.name.isNotBlank()) currencyNamesList.add(KPW.name)
        if (KRW.name.isNotBlank()) currencyNamesList.add(KRW.name)
        if (KWD.name.isNotBlank()) currencyNamesList.add(KWD.name)
        if (KYD.name.isNotBlank()) currencyNamesList.add(KYD.name)
        if (KZT.name.isNotBlank()) currencyNamesList.add(KZT.name)
        if (LAK.name.isNotBlank()) currencyNamesList.add(LAK.name)
        if (LBP.name.isNotBlank()) currencyNamesList.add(LBP.name)
        if (LKR.name.isNotBlank()) currencyNamesList.add(LKR.name)
        if (LRD.name.isNotBlank()) currencyNamesList.add(LRD.name)
        if (LSL.name.isNotBlank()) currencyNamesList.add(LSL.name)
        if (LYD.name.isNotBlank()) currencyNamesList.add(LYD.name)
        if (MAD.name.isNotBlank()) currencyNamesList.add(MAD.name)
        if (MDL.name.isNotBlank()) currencyNamesList.add(MDL.name)
        if (MGA.name.isNotBlank()) currencyNamesList.add(MGA.name)
        if (MKD.name.isNotBlank()) currencyNamesList.add(MKD.name)
        if (MMK.name.isNotBlank()) currencyNamesList.add(MMK.name)
        if (MNT.name.isNotBlank()) currencyNamesList.add(MNT.name)
        if (MOP.name.isNotBlank()) currencyNamesList.add(MOP.name)
        if (MRU.name.isNotBlank()) currencyNamesList.add(MRU.name)
        if (MUR.name.isNotBlank()) currencyNamesList.add(MUR.name)
        if (MVR.name.isNotBlank()) currencyNamesList.add(MVR.name)
        if (MWK.name.isNotBlank()) currencyNamesList.add(MWK.name)
        if (MXN.name.isNotBlank()) currencyNamesList.add(MXN.name)
        if (MYR.name.isNotBlank()) currencyNamesList.add(MYR.name)
        if (MZN.name.isNotBlank()) currencyNamesList.add(MZN.name)
        if (NAD.name.isNotBlank()) currencyNamesList.add(NAD.name)
        if (NGN.name.isNotBlank()) currencyNamesList.add(NGN.name)
        if (NIO.name.isNotBlank()) currencyNamesList.add(NIO.name)
        if (NOK.name.isNotBlank()) currencyNamesList.add(NOK.name)
        if (NPR.name.isNotBlank()) currencyNamesList.add(NPR.name)
        if (NZD.name.isNotBlank()) currencyNamesList.add(NZD.name)
        if (OMR.name.isNotBlank()) currencyNamesList.add(OMR.name)
        if (PAB.name.isNotBlank()) currencyNamesList.add(PAB.name)
        if (PEN.name.isNotBlank()) currencyNamesList.add(PEN.name)
        if (PGK.name.isNotBlank()) currencyNamesList.add(PGK.name)
        if (PHP.name.isNotBlank()) currencyNamesList.add(PHP.name)
        if (PKR.name.isNotBlank()) currencyNamesList.add(PKR.name)
        if (PLN.name.isNotBlank()) currencyNamesList.add(PLN.name)
        if (PYG.name.isNotBlank()) currencyNamesList.add(PYG.name)
        if (QAR.name.isNotBlank()) currencyNamesList.add(QAR.name)
        if (RON.name.isNotBlank()) currencyNamesList.add(RON.name)
        if (RSD.name.isNotBlank()) currencyNamesList.add(RSD.name)
        if (RUB.name.isNotBlank()) currencyNamesList.add(RUB.name)
        if (RWF.name.isNotBlank()) currencyNamesList.add(RWF.name)
        if (SAR.name.isNotBlank()) currencyNamesList.add(SAR.name)
        if (SBD.name.isNotBlank()) currencyNamesList.add(SBD.name)
        if (SCR.name.isNotBlank()) currencyNamesList.add(SCR.name)
        if (SDG.name.isNotBlank()) currencyNamesList.add(SDG.name)
        if (SEK.name.isNotBlank()) currencyNamesList.add(SEK.name)
        if (SGD.name.isNotBlank()) currencyNamesList.add(SGD.name)
        if (SHP.name.isNotBlank()) currencyNamesList.add(SHP.name)
        if (SLL.name.isNotBlank()) currencyNamesList.add(SLL.name)
        if (SOS.name.isNotBlank()) currencyNamesList.add(SOS.name)
        if (SRD.name.isNotBlank()) currencyNamesList.add(SRD.name)
        if (SSP.name.isNotBlank()) currencyNamesList.add(SSP.name)
        if (STN.name.isNotBlank()) currencyNamesList.add(STN.name)
        if (SYP.name.isNotBlank()) currencyNamesList.add(SYP.name)
        if (SZL.name.isNotBlank()) currencyNamesList.add(SZL.name)
        if (THB.name.isNotBlank()) currencyNamesList.add(THB.name)
        if (TJS.name.isNotBlank()) currencyNamesList.add(TJS.name)
        if (TMT.name.isNotBlank()) currencyNamesList.add(TMT.name)
        if (TND.name.isNotBlank()) currencyNamesList.add(TND.name)
        if (TOP.name.isNotBlank()) currencyNamesList.add(TOP.name)
        if (TRY.name.isNotBlank()) currencyNamesList.add(TRY.name)
        if (TTD.name.isNotBlank()) currencyNamesList.add(TTD.name)
        if (TVD.name.isNotBlank()) currencyNamesList.add(TVD.name)
        if (TWD.name.isNotBlank()) currencyNamesList.add(TWD.name)
        if (TZS.name.isNotBlank()) currencyNamesList.add(TZS.name)
        if (UAH.name.isNotBlank()) currencyNamesList.add(UAH.name)
        if (UGX.name.isNotBlank()) currencyNamesList.add(UGX.name)
        if (USD.name.isNotBlank()) currencyNamesList.add(USD.name)
        if (UYU.name.isNotBlank()) currencyNamesList.add(UYU.name)
        if (UZS.name.isNotBlank()) currencyNamesList.add(UZS.name)
        if (VES.name.isNotBlank()) currencyNamesList.add(VES.name)
        if (VND.name.isNotBlank()) currencyNamesList.add(VND.name)
        if (VUV.name.isNotBlank()) currencyNamesList.add(VUV.name)
        if (WST.name.isNotBlank()) currencyNamesList.add(WST.name)
        if (XAF.name.isNotBlank()) currencyNamesList.add(XAF.name)
        if (XCD.name.isNotBlank()) currencyNamesList.add(XCD.name)
        if (XOF.name.isNotBlank()) currencyNamesList.add(XOF.name)
        if (XPF.name.isNotBlank()) currencyNamesList.add(XPF.name)
        if (YER.name.isNotBlank()) currencyNamesList.add(YER.name)
        if (ZAR.name.isNotBlank()) currencyNamesList.add(ZAR.name)
        if (ZMW.name.isNotBlank()) currencyNamesList.add(ZMW.name)
        if (ZWL.name.isNotBlank()) currencyNamesList.add(ZWL.name)

        return currencyNamesList
    }
}

@Serializable
data class AED(val name: String = "", val symbol: String = "")

@Serializable
data class AFN(val name: String = "", val symbol: String = "")

@Serializable
data class ALL(val name: String = "", val symbol: String = "")

@Serializable
data class AMD(val name: String = "", val symbol: String = "")

@Serializable
data class ANG(val name: String = "", val symbol: String = "")

@Serializable
data class AOA(val name: String = "", val symbol: String = "")

@Serializable
data class ARS(val name: String = "", val symbol: String = "")

@Serializable
data class AUD(val name: String = "", val symbol: String = "")

@Serializable
data class AWG(val name: String = "", val symbol: String = "")

@Serializable
data class AZN(val name: String = "", val symbol: String = "")

@Serializable
data class BAM(val name: String = "", val symbol: String = "")

@Serializable
data class BBD(val name: String = "", val symbol: String = "")

@Serializable
data class BDT(val name: String = "", val symbol: String = "")

@Serializable
data class BGN(val name: String = "", val symbol: String = "")

@Serializable
data class BHD(val name: String = "", val symbol: String = "")

@Serializable
data class BIF(val name: String = "", val symbol: String = "")

@Serializable
data class BMD(val name: String = "", val symbol: String = "")

@Serializable
data class BND(val name: String = "", val symbol: String = "")

@Serializable
data class BOB(val name: String = "", val symbol: String = "")

@Serializable
data class BRL(val name: String = "", val symbol: String = "")

@Serializable
data class BSD(val name: String = "", val symbol: String = "")

@Serializable
data class BTN(val name: String = "", val symbol: String = "")

@Serializable
data class BWP(val name: String = "", val symbol: String = "")

@Serializable
data class BYN(val name: String = "", val symbol: String = "")

@Serializable
data class BZD(val name: String = "", val symbol: String = "")

@Serializable
data class CAD(val name: String = "", val symbol: String = "")

@Serializable
data class CDF(val name: String = "", val symbol: String = "")

@Serializable
data class CHF(val name: String = "", val symbol: String = "")

@Serializable
data class CKD(val name: String = "", val symbol: String = "")

@Serializable
data class CLP(val name: String = "", val symbol: String = "")

@Serializable
data class CNY(val name: String = "", val symbol: String = "")

@Serializable
data class COP(val name: String = "", val symbol: String = "")

@Serializable
data class CRC(val name: String = "", val symbol: String = "")

@Serializable
data class CUC(val name: String = "", val symbol: String = "")

@Serializable
data class CUP(val name: String = "", val symbol: String = "")

@Serializable
data class CVE(val name: String = "", val symbol: String = "")

@Serializable
data class CZK(val name: String = "", val symbol: String = "")

@Serializable
data class DJF(val name: String = "", val symbol: String = "")

@Serializable
data class DKK(val name: String = "", val symbol: String = "")

@Serializable
data class DOP(val name: String = "", val symbol: String = "")

@Serializable
data class DZD(val name: String = "", val symbol: String = "")

@Serializable
data class EGP(val name: String = "", val symbol: String = "")

@Serializable
data class ERN(val name: String = "", val symbol: String = "")

@Serializable
data class ETB(val name: String = "", val symbol: String = "")

@Serializable
data class EUR(val name: String = "", val symbol: String = "")

@Serializable
data class FJD(val name: String = "", val symbol: String = "")

@Serializable
data class FKP(val name: String = "", val symbol: String = "")

@Serializable
data class FOK(val name: String = "", val symbol: String = "")

@Serializable
data class GBP(val name: String = "", val symbol: String = "")

@Serializable
data class GEL(val name: String = "", val symbol: String = "")

@Serializable
data class GGP(val name: String = "", val symbol: String = "")

@Serializable
data class GHS(val name: String = "", val symbol: String = "")

@Serializable
data class GIP(val name: String = "", val symbol: String = "")

@Serializable
data class GMD(val name: String = "", val symbol: String = "")

@Serializable
data class GNF(val name: String = "", val symbol: String = "")

@Serializable
data class GTQ(val name: String = "", val symbol: String = "")

@Serializable
data class GYD(val name: String = "", val symbol: String = "")

@Serializable
data class HKD(val name: String = "", val symbol: String = "")

@Serializable
data class HNL(val name: String = "", val symbol: String = "")

@Serializable
data class HTG(val name: String = "", val symbol: String = "")

@Serializable
data class HUF(val name: String = "", val symbol: String = "")

@Serializable
data class IDR(val name: String = "", val symbol: String = "")

@Serializable
data class ILS(val name: String = "", val symbol: String = "")

@Serializable
data class IMP(val name: String = "", val symbol: String = "")

@Serializable
data class INR(val name: String = "", val symbol: String = "")

@Serializable
data class IQD(val name: String = "", val symbol: String = "")

@Serializable
data class IRR(val name: String = "", val symbol: String = "")

@Serializable
data class ISK(val name: String = "", val symbol: String = "")

@Serializable
data class JEP(val name: String = "", val symbol: String = "")

@Serializable
data class JMD(val name: String = "", val symbol: String = "")

@Serializable
data class JOD(val name: String = "", val symbol: String = "")

@Serializable
data class JPY(val name: String = "", val symbol: String = "")

@Serializable
data class KES(val name: String = "", val symbol: String = "")

@Serializable
data class KGS(val name: String = "", val symbol: String = "")

@Serializable
data class KHR(val name: String = "", val symbol: String = "")

@Serializable
data class KID(val name: String = "", val symbol: String = "")

@Serializable
data class KMF(val name: String = "", val symbol: String = "")

@Serializable
data class KPW(val name: String = "", val symbol: String = "")

@Serializable
data class KRW(val name: String = "", val symbol: String = "")

@Serializable
data class KWD(val name: String = "", val symbol: String = "")

@Serializable
data class KYD(val name: String = "", val symbol: String = "")

@Serializable
data class KZT(val name: String = "", val symbol: String = "")

@Serializable
data class LAK(val name: String = "", val symbol: String = "")

@Serializable
data class LBP(val name: String = "", val symbol: String = "")

@Serializable
data class LKR(val name: String = "", val symbol: String = "")

@Serializable
data class LRD(val name: String = "", val symbol: String = "")

@Serializable
data class LSL(val name: String = "", val symbol: String = "")

@Serializable
data class LYD(val name: String = "", val symbol: String = "")

@Serializable
data class MAD(val name: String = "", val symbol: String = "")

@Serializable
data class MDL(val name: String = "", val symbol: String = "")

@Serializable
data class MGA(val name: String = "", val symbol: String = "")

@Serializable
data class MKD(val name: String = "", val symbol: String = "")

@Serializable
data class MMK(val name: String = "", val symbol: String = "")

@Serializable
data class MNT(val name: String = "", val symbol: String = "")

@Serializable
data class MOP(val name: String = "", val symbol: String = "")

@Serializable
data class MRU(val name: String = "", val symbol: String = "")

@Serializable
data class MUR(val name: String = "", val symbol: String = "")

@Serializable
data class MVR(val name: String = "", val symbol: String = "")

@Serializable
data class MWK(val name: String = "", val symbol: String = "")

@Serializable
data class MXN(val name: String = "", val symbol: String = "")

@Serializable
data class MYR(val name: String = "", val symbol: String = "")

@Serializable
data class MZN(val name: String = "", val symbol: String = "")

@Serializable
data class NAD(val name: String = "", val symbol: String = "")

@Serializable
data class NGN(val name: String = "", val symbol: String = "")

@Serializable
data class NIO(val name: String = "", val symbol: String = "")

@Serializable
data class NOK(val name: String = "", val symbol: String = "")

@Serializable
data class NPR(val name: String = "", val symbol: String = "")

@Serializable
data class NZD(val name: String = "", val symbol: String = "")

@Serializable
data class OMR(val name: String = "", val symbol: String = "")

@Serializable
data class PAB(val name: String = "", val symbol: String = "")

@Serializable
data class PEN(val name: String = "", val symbol: String = "")

@Serializable
data class PGK(val name: String = "", val symbol: String = "")

@Serializable
data class PHP(val name: String = "", val symbol: String = "")

@Serializable
data class PKR(val name: String = "", val symbol: String = "")

@Serializable
data class PLN(val name: String = "", val symbol: String = "")

@Serializable
data class PYG(val name: String = "", val symbol: String = "")

@Serializable
data class QAR(val name: String = "", val symbol: String = "")

@Serializable
data class RON(val name: String = "", val symbol: String = "")

@Serializable
data class RSD(val name: String = "", val symbol: String = "")

@Serializable
data class RUB(val name: String = "", val symbol: String = "")

@Serializable
data class RWF(val name: String = "", val symbol: String = "")

@Serializable
data class SAR(val name: String = "", val symbol: String = "")

@Serializable
data class SBD(val name: String = "", val symbol: String = "")

@Serializable
data class SCR(val name: String = "", val symbol: String = "")

@Serializable
data class SDG(val name: String = "", val symbol: String = "")

@Serializable
data class SEK(val name: String = "", val symbol: String = "")

@Serializable
data class SGD(val name: String = "", val symbol: String = "")

@Serializable
data class SHP(val name: String = "", val symbol: String = "")

@Serializable
data class SLL(val name: String = "", val symbol: String = "")

@Serializable
data class SOS(val name: String = "", val symbol: String = "")

@Serializable
data class SRD(val name: String = "", val symbol: String = "")

@Serializable
data class SSP(val name: String = "", val symbol: String = "")

@Serializable
data class STN(val name: String = "", val symbol: String = "")

@Serializable
data class SYP(val name: String = "", val symbol: String = "")

@Serializable
data class SZL(val name: String = "", val symbol: String = "")

@Serializable
data class THB(val name: String = "", val symbol: String = "")

@Serializable
data class TJS(val name: String = "", val symbol: String = "")

@Serializable
data class TMT(val name: String = "", val symbol: String = "")

@Serializable
data class TND(val name: String = "", val symbol: String = "")

@Serializable
data class TOP(val name: String = "", val symbol: String = "")

@Serializable
data class TRY(val name: String = "", val symbol: String = "")

@Serializable
data class TTD(val name: String = "", val symbol: String = "")

@Serializable
data class TVD(val name: String = "", val symbol: String = "")

@Serializable
data class TWD(val name: String = "", val symbol: String = "")

@Serializable
data class TZS(val name: String = "", val symbol: String = "")

@Serializable
data class UAH(val name: String = "", val symbol: String = "")

@Serializable
data class UGX(val name: String = "", val symbol: String = "")

@Serializable
data class USD(val name: String = "", val symbol: String = "")

@Serializable
data class UYU(val name: String = "", val symbol: String = "")

@Serializable
data class UZS(val name: String = "", val symbol: String = "")

@Serializable
data class VES(val name: String = "", val symbol: String = "")

@Serializable
data class VND(val name: String = "", val symbol: String = "")

@Serializable
data class VUV(val name: String = "", val symbol: String = "")

@Serializable
data class WST(val name: String = "", val symbol: String = "")

@Serializable
data class XAF(val name: String = "", val symbol: String = "")

@Serializable
data class XCD(val name: String = "", val symbol: String = "")

@Serializable
data class XOF(val name: String = "", val symbol: String = "")

@Serializable
data class XPF(val name: String = "", val symbol: String = "")

@Serializable
data class YER(val name: String = "", val symbol: String = "")

@Serializable
data class ZAR(val name: String = "", val symbol: String = "")

@Serializable
data class ZMW(val name: String = "", val symbol: String = "")

@Serializable
data class ZWL(val name: String = "", val symbol: String = "")


