package com.pscode.app.data.model.country

import kotlinx.serialization.Serializable

@Serializable
data class Languages(
    val afr: String = "",
    val amh: String = "",
    val ara: String = "",
    val arc: String = "",
    val aym: String = "",
    val aze: String = "",
    val bel: String = "",
    val ben: String = "",
    val ber: String = "",
    val bis: String = "",
    val bjz: String = "",
    val bos: String = "",
    val bul: String = "",
    val bwg: String = "",
    val cal: String = "",
    val cat: String = "",
    val ces: String = "",
    val cha: String = "",
    val ckb: String = "",
    val cnr: String = "",
    val crs: String = "",
    val dan: String = "",
    val de: String = "",
    val deu: String = "",
    val div: String = "",
    val dzo: String = "",
    val ell: String = "",
    val eng: String = "",
    val est: String = "",
    val eus: String = "",
    val fao: String = "",
    val fas: String = "",
    val fij: String = "",
    val fil: String = "",
    val fin: String = "",
    val fra: String = "",
    val gil: String = "",
    val glc: String = "",
    val gle: String = "",
    val glv: String = "",
    val grn: String = "",
    val gsw: String = "",
    val hat: String = "",
    val heb: String = "",
    val her: String = "",
    val hgm: String = "",
    val hif: String = "",
    val hin: String = "",
    val hmo: String = "",
    val hrv: String = "",
    val hun: String = "",
    val hye: String = "",
    val ind: String = "",
    val isl: String = "",
    val ita: String = "",
    val jam: String = "",
    val jpn: String = "",
    val kal: String = "",
    val kat: String = "",
    val kaz: String = "",
    val kck: String = "",
    val khi: String = "",
    val khm: String = "",
    val kin: String = "",
    val kir: String = "",
    val kon: String = "",
    val kor: String = "",
    val kwn: String = "",
    val lao: String = "",
    val lat: String = "",
    val lav: String = "",
    val lin: String = "",
    val lit: String = "",
    val loz: String = "",
    val ltz: String = "",
    val lua: String = "",
    val mah: String = "",
    val mey: String = "",
    val mfe: String = "",
    val mkd: String = "",
    val mlg: String = "",
    val mlt: String = "",
    val mon: String = "",
    val mri: String = "",
    val msa: String = "",
    val mya: String = "",
    val nau: String = "",
    val nbl: String = "",
    val ndc: String = "",
    val nde: String = "",
    val ndo: String = "",
    val nep: String = "",
    val nfr: String = "",
    val niu: String = "",
    val nld: String = "",
    val nno: String = "",
    val nob: String = "",
    val nor: String = "",
    val nrf: String = "",
    val nso: String = "",
    val nya: String = "",
    val nzs: String = "",
    val pap: String = "",
    val pau: String = "",
    val pih: String = "",
    val pol: String = "",
    val por: String = "",
    val pov: String = "",
    val prs: String = "",
    val pus: String = "",
    val que: String = "",
    val rar: String = "",
    val roh: String = "",
    val ron: String = "",
    val run: String = "",
    val rus: String = "",
    val sag: String = "",
    val sin: String = "",
    val slk: String = "",
    val slv: String = "",
    val smi: String = "",
    val smo: String = "",
    val sna: String = "",
    val som: String = "",
    val sot: String = "",
    val spa: String = "",
    val sqi: String = "",
    val srp: String = "",
    val ssw: String = "",
    val swa: String = "",
    val swe: String = "",
    val tam: String = "",
    val tet: String = "",
    val tgk: String = "",
    val tha: String = "",
    val tir: String = "",
    val tkl: String = "",
    val toi: String = "",
    val ton: String = "",
    val tpi: String = "",
    val tsn: String = "",
    val tso: String = "",
    val tuk: String = "",
    val tur: String = "",
    val tvl: String = "",
    val ukr: String = "",
    val urd: String = "",
    val uzb: String = "",
    val ven: String = "",
    val vie: String = "",
    val xho: String = "",
    val zdj: String = "",
    val zho: String = "",
    val zib: String = "",
    val zul: String = ""
) {
    fun toNonEmptyList(): List<String> {
        val languagesList = mutableListOf<String>()

        if (afr.isNotBlank()) languagesList.add(afr)
        if (amh.isNotBlank()) languagesList.add(amh)
        if (ara.isNotBlank()) languagesList.add(ara)
        if (arc.isNotBlank()) languagesList.add(arc)
        if (aym.isNotBlank()) languagesList.add(aym)
        if (aze.isNotBlank()) languagesList.add(aze)
        if (bel.isNotBlank()) languagesList.add(bel)
        if (ben.isNotBlank()) languagesList.add(ben)
        if (ber.isNotBlank()) languagesList.add(ber)
        if (bis.isNotBlank()) languagesList.add(bis)
        if (bjz.isNotBlank()) languagesList.add(bjz)
        if (bos.isNotBlank()) languagesList.add(bos)
        if (bul.isNotBlank()) languagesList.add(bul)
        if (bwg.isNotBlank()) languagesList.add(bwg)
        if (cal.isNotBlank()) languagesList.add(cal)
        if (cat.isNotBlank()) languagesList.add(cat)
        if (ces.isNotBlank()) languagesList.add(ces)
        if (cha.isNotBlank()) languagesList.add(cha)
        if (ckb.isNotBlank()) languagesList.add(ckb)
        if (cnr.isNotBlank()) languagesList.add(cnr)
        if (crs.isNotBlank()) languagesList.add(crs)
        if (dan.isNotBlank()) languagesList.add(dan)
        if (de.isNotBlank()) languagesList.add(de)
        if (deu.isNotBlank()) languagesList.add(deu)
        if (div.isNotBlank()) languagesList.add(div)
        if (dzo.isNotBlank()) languagesList.add(dzo)
        if (ell.isNotBlank()) languagesList.add(ell)
        if (eng.isNotBlank()) languagesList.add(eng)
        if (est.isNotBlank()) languagesList.add(est)
        if (eus.isNotBlank()) languagesList.add(eus)
        if (fao.isNotBlank()) languagesList.add(fao)
        if (fas.isNotBlank()) languagesList.add(fas)
        if (fij.isNotBlank()) languagesList.add(fij)
        if (fil.isNotBlank()) languagesList.add(fil)
        if (fin.isNotBlank()) languagesList.add(fin)
        if (fra.isNotBlank()) languagesList.add(fra)
        if (gil.isNotBlank()) languagesList.add(gil)
        if (glc.isNotBlank()) languagesList.add(glc)
        if (gle.isNotBlank()) languagesList.add(gle)
        if (glv.isNotBlank()) languagesList.add(glv)
        if (grn.isNotBlank()) languagesList.add(grn)
        if (gsw.isNotBlank()) languagesList.add(gsw)
        if (hat.isNotBlank()) languagesList.add(hat)
        if (heb.isNotBlank()) languagesList.add(heb)
        if (her.isNotBlank()) languagesList.add(her)
        if (hgm.isNotBlank()) languagesList.add(hgm)
        if (hif.isNotBlank()) languagesList.add(hif)
        if (hin.isNotBlank()) languagesList.add(hin)
        if (hmo.isNotBlank()) languagesList.add(hmo)
        if (hrv.isNotBlank()) languagesList.add(hrv)
        if (hun.isNotBlank()) languagesList.add(hun)
        if (hye.isNotBlank()) languagesList.add(hye)
        if (ind.isNotBlank()) languagesList.add(ind)
        if (isl.isNotBlank()) languagesList.add(isl)
        if (ita.isNotBlank()) languagesList.add(ita)
        if (jam.isNotBlank()) languagesList.add(jam)
        if (jpn.isNotBlank()) languagesList.add(jpn)
        if (kal.isNotBlank()) languagesList.add(kal)
        if (kat.isNotBlank()) languagesList.add(kat)
        if (kaz.isNotBlank()) languagesList.add(kaz)
        if (kck.isNotBlank()) languagesList.add(kck)
        if (khi.isNotBlank()) languagesList.add(khi)
        if (khm.isNotBlank()) languagesList.add(khm)
        if (kin.isNotBlank()) languagesList.add(kin)
        if (kir.isNotBlank()) languagesList.add(kir)
        if (kon.isNotBlank()) languagesList.add(kon)
        if (kor.isNotBlank()) languagesList.add(kor)
        if (kwn.isNotBlank()) languagesList.add(kwn)
        if (lao.isNotBlank()) languagesList.add(lao)
        if (lat.isNotBlank()) languagesList.add(lat)
        if (lav.isNotBlank()) languagesList.add(lav)
        if (lin.isNotBlank()) languagesList.add(lin)
        if (lit.isNotBlank()) languagesList.add(lit)
        if (loz.isNotBlank()) languagesList.add(loz)
        if (ltz.isNotBlank()) languagesList.add(ltz)
        if (lua.isNotBlank()) languagesList.add(lua)
        if (mah.isNotBlank()) languagesList.add(mah)
        if (mey.isNotBlank()) languagesList.add(mey)
        if (mfe.isNotBlank()) languagesList.add(mfe)
        if (mkd.isNotBlank()) languagesList.add(mkd)
        if (mlg.isNotBlank()) languagesList.add(mlg)
        if (mlt.isNotBlank()) languagesList.add(mlt)
        if (mon.isNotBlank()) languagesList.add(mon)
        if (mri.isNotBlank()) languagesList.add(mri)
        if (msa.isNotBlank()) languagesList.add(msa)
        if (mya.isNotBlank()) languagesList.add(mya)
        if (nau.isNotBlank()) languagesList.add(nau)
        if (nbl.isNotBlank()) languagesList.add(nbl)
        if (ndc.isNotBlank()) languagesList.add(ndc)
        if (nde.isNotBlank()) languagesList.add(nde)
        if (ndo.isNotBlank()) languagesList.add(ndo)
        if (nep.isNotBlank()) languagesList.add(nep)
        if (nfr.isNotBlank()) languagesList.add(nfr)
        if (niu.isNotBlank()) languagesList.add(niu)
        if (nld.isNotBlank()) languagesList.add(nld)
        if (nno.isNotBlank()) languagesList.add(nno)
        if (nob.isNotBlank()) languagesList.add(nob)
        if (nor.isNotBlank()) languagesList.add(nor)
        if (nrf.isNotBlank()) languagesList.add(nrf)
        if (nso.isNotBlank()) languagesList.add(nso)
        if (nya.isNotBlank()) languagesList.add(nya)
        if (nzs.isNotBlank()) languagesList.add(nzs)
        if (pap.isNotBlank()) languagesList.add(pap)
        if (pau.isNotBlank()) languagesList.add(pau)
        if (pih.isNotBlank()) languagesList.add(pih)
        if (pol.isNotBlank()) languagesList.add(pol)
        if (por.isNotBlank()) languagesList.add(por)
        if (pov.isNotBlank()) languagesList.add(pov)
        if (prs.isNotBlank()) languagesList.add(prs)
        if (pus.isNotBlank()) languagesList.add(pus)
        if (que.isNotBlank()) languagesList.add(que)
        if (rar.isNotBlank()) languagesList.add(rar)
        if (roh.isNotBlank()) languagesList.add(roh)
        if (ron.isNotBlank()) languagesList.add(ron)
        if (run.isNotBlank()) languagesList.add(run)
        if (rus.isNotBlank()) languagesList.add(rus)
        if (sag.isNotBlank()) languagesList.add(sag)
        if (sin.isNotBlank()) languagesList.add(sin)
        if (slk.isNotBlank()) languagesList.add(slk)
        if (slv.isNotBlank()) languagesList.add(slv)
        if (smi.isNotBlank()) languagesList.add(smi)
        if (smo.isNotBlank()) languagesList.add(smo)
        if (sna.isNotBlank()) languagesList.add(sna)
        if (som.isNotBlank()) languagesList.add(som)
        if (sot.isNotBlank()) languagesList.add(sot)
        if (spa.isNotBlank()) languagesList.add(spa)
        if (sqi.isNotBlank()) languagesList.add(sqi)
        if (srp.isNotBlank()) languagesList.add(srp)
        if (ssw.isNotBlank()) languagesList.add(ssw)
        if (swa.isNotBlank()) languagesList.add(swa)
        if (swe.isNotBlank()) languagesList.add(swe)
        if (tam.isNotBlank()) languagesList.add(tam)
        if (tet.isNotBlank()) languagesList.add(tet)
        if (tgk.isNotBlank()) languagesList.add(tgk)
        if (tha.isNotBlank()) languagesList.add(tha)
        if (tir.isNotBlank()) languagesList.add(tir)
        if (tkl.isNotBlank()) languagesList.add(tkl)
        if (toi.isNotBlank()) languagesList.add(toi)
        if (ton.isNotBlank()) languagesList.add(ton)
        if (tpi.isNotBlank()) languagesList.add(tpi)
        if (tsn.isNotBlank()) languagesList.add(tsn)
        if (tso.isNotBlank()) languagesList.add(tso)
        if (tuk.isNotBlank()) languagesList.add(tuk)
        if (tur.isNotBlank()) languagesList.add(tur)
        if (tvl.isNotBlank()) languagesList.add(tvl)
        if (ukr.isNotBlank()) languagesList.add(ukr)
        if (urd.isNotBlank()) languagesList.add(urd)
        if (uzb.isNotBlank()) languagesList.add(uzb)
        if (ven.isNotBlank()) languagesList.add(ven)
        if (vie.isNotBlank()) languagesList.add(vie)
        if (xho.isNotBlank()) languagesList.add(xho)
        if (zdj.isNotBlank()) languagesList.add(zdj)
        if (zho.isNotBlank()) languagesList.add(zho)
        if (zib.isNotBlank()) languagesList.add(zib)
        if (zul.isNotBlank()) languagesList.add(zul)

        return languagesList
    }
}