package com.aten.mn.line.charts;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.jensoft.sw2d.core.palette.ColorPalette;
import com.jensoft.sw2d.core.palette.InputFonts;
import com.jensoft.sw2d.core.palette.PetalPalette;
import com.jensoft.sw2d.core.palette.RosePalette;
import com.jensoft.sw2d.core.palette.TangoPalette;
import com.jensoft.sw2d.core.plugin.grid.GridPlugin;
import com.jensoft.sw2d.core.plugin.grid.manager.DynamicGridManager;
import com.jensoft.sw2d.core.plugin.legend.Legend;
import com.jensoft.sw2d.core.plugin.legend.LegendConstraints;
import com.jensoft.sw2d.core.plugin.legend.LegendPlugin;
import com.jensoft.sw2d.core.plugin.legend.painter.fill.LegendFill1;
import com.jensoft.sw2d.core.plugin.metrics.axis.AxisMilliMetrics;
import com.jensoft.sw2d.core.plugin.outline.OutlinePlugin;
import com.jensoft.sw2d.core.plugin.pie.Pie;
import com.jensoft.sw2d.core.plugin.pie.PiePlugin;
import com.jensoft.sw2d.core.plugin.pie.PieSlice;
import com.jensoft.sw2d.core.plugin.pie.PieToolkit;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.AbstractPieEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.PieCompoundEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.PieReflectionEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.label.PieBorderLabel;
import com.jensoft.sw2d.core.plugin.stripe.StripePlugin;
import com.jensoft.sw2d.core.plugin.stripe.manager.DynamicStripeManager;
import com.jensoft.sw2d.core.plugin.stripe.painter.StripePalette;
import com.jensoft.sw2d.core.plugin.symbol.BarSymbol;
import com.jensoft.sw2d.core.plugin.symbol.BarSymbolGroup;
import com.jensoft.sw2d.core.plugin.symbol.BarSymbolLayer;
import com.jensoft.sw2d.core.plugin.symbol.SymbolComponent;
import com.jensoft.sw2d.core.plugin.symbol.SymbolPlugin;
import com.jensoft.sw2d.core.plugin.symbol.SymbolToolkit;
import com.jensoft.sw2d.core.plugin.symbol.painter.axis.BarDefaultAxisLabel;
import com.jensoft.sw2d.core.plugin.symbol.painter.draw.BarDefaultDraw;
import com.jensoft.sw2d.core.plugin.symbol.painter.effect.BarEffect4;
import com.jensoft.sw2d.core.plugin.symbol.painter.fill.BarFill1;
import com.jensoft.sw2d.core.view.View2D;
import com.jensoft.sw2d.core.window.WindowPart;

public class Charts {

	private View2D view;

	public static void main(String[]args) {
		Charts charts = new Charts();
		int index = 30;
		int minimum = 10;
		int maximum = 1000;
		String[] lable = new String[index];
		double[] value = new double[index];
		int[] node = new int[index];
		for(int i=0;i<index;i++) {
			lable[i] = "00"+i;
			value[i] = minimum + (int)(Math.random() * maximum);
			node[i] = minimum + (int)(Math.random() * maximum);
		}
		charts.genImage("GOSS",lable,value,node,18D,1200,1200);
	}

	public void genImage(String coin, String[] lable, double[] value, int[] node, double persentCoinLock, double maxNode, double maxValue) {
		try {
			view = new View2D();
			view.setName(coin);
			view.setBackground(Color.BLACK);
			view.getWidgetPlugin().pushMessage(coin, 0, null,
					com.jensoft.sw2d.core.view.WidgetPlugin.PushingBehavior.Fast,
					InputFonts.getNeuropol(14), Color.WHITE);


			Color color = PetalPalette.PETAL1_HC;
			//            Color color1 = PetalPalette.PETAL2_HC;
			// Color color2 = PetalPalette.PETAL3_HC;
			Color color2 = Color.RED;
			view.repaintView();
			com.jensoft.sw2d.core.window.Window2D.Linear linear = new com.jensoft.sw2d.core.window.Window2D.Linear(
					0.0D, 0.0D, 0.0D, maxValue);
			linear.setName("compatible vertical bar window");
			view.registerWindow2D(linear);
			LegendPlugin legendplugin = new LegendPlugin();
			linear.registerPlugin(legendplugin);
			linear.setThemeColor(RosePalette.MELON);
			linear.registerPlugin(new OutlinePlugin());
			AxisMilliMetrics axismillimetrics = new AxisMilliMetrics(0.0D,
					com.jensoft.sw2d.core.plugin.metrics.axis.AxisMetricsPlugin.Axis.AxisWest);
			axismillimetrics.setMajor(((maxValue/10)*6)-(((maxValue/10)*6)%10));
			axismillimetrics.setMedian(((maxValue/10)*3)-(((maxValue/10)*3)%10));
			axismillimetrics.setMinor(((maxValue/10)*1)-(((maxValue/10)*1)%10));
			axismillimetrics.setMetricsLabelColor(color);
			axismillimetrics.setMetricsMarkerColor(color);
			linear.registerPlugin(axismillimetrics);
			linear.setThemeColor(RosePalette.MELON);
			view.repaintView();
			DynamicStripeManager dynamicstripemanager = new DynamicStripeManager(
					com.jensoft.sw2d.core.plugin.stripe.StripePlugin.StripeOrientation.Horizontal,
					0.0D, 30D);
			StripePlugin stripeplugin = new StripePlugin(dynamicstripemanager);
			StripePalette stripepalette = new StripePalette();
			stripepalette.addPaint(new Color(255, 255, 255, 40));
			stripepalette.addPaint(ColorPalette.alpha(TangoPalette.ORANGE3, 40));
			dynamicstripemanager.setBandPalette(stripepalette);
			stripeplugin.setAlpha(0.3F);
			linear.registerPlugin(stripeplugin);
			view.repaintDevice();
			DynamicGridManager dynamicgridmanager = new DynamicGridManager(
					com.jensoft.sw2d.core.plugin.grid.Grid.GridOrientation.Horizontal, 0.0D,
					5D);
			dynamicgridmanager.setGridColor(new Color(255, 255, 255, 60));
			GridPlugin gridplugin = new GridPlugin(dynamicgridmanager);
			linear.registerPlugin(gridplugin);
			view.repaintDevice();
			Legend legend1 = new Legend("Price");
			legend1.setLegendFill(new LegendFill1(Color.WHITE, color));
			legend1.setFont(InputFonts.getFont(InputFonts.NO_MOVE, 14));
			legend1
			.setConstraints(new LegendConstraints(
					com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendPosition.West,
					0.5F,
					com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendAlignment.Middle));
			legendplugin.addLegend(legend1);
			view.repaintDevice();

			int index_01 = value.length;
			BarSymbol abarsymbol[] = new BarSymbol[index_01];
			for(int i=0;i<index_01;i++) {
				BarSymbol barsymbol = SymbolToolkit.createBarSymbol(lable[i], color,
						com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Descent,
						value[i]);
				abarsymbol[i] = barsymbol;
			}

			BarSymbolGroup barsymbolgroup = new BarSymbolGroup("G1");
			barsymbolgroup.setBase(0.0D);
			barsymbolgroup.setThickness(25D);
			barsymbolgroup.setRound(8);
			barsymbolgroup
			.setMorpheStyle(com.jensoft.sw2d.core.plugin.symbol.BarSymbol.MorpheStyle.Round);
			barsymbolgroup.setBarDraw(new BarDefaultDraw(Color.WHITE));
			barsymbolgroup.setBarFill(new BarFill1());
			barsymbolgroup.setBarEffect(new BarEffect4());
			SymbolPlugin symbolplugin = new SymbolPlugin();
			symbolplugin
			.setNature(com.jensoft.sw2d.core.plugin.symbol.SymbolPlugin.SymbolNature.Vertical);
			symbolplugin.setPriority(100);
			linear.registerPlugin(symbolplugin);
			BarSymbolLayer barsymbollayer = new BarSymbolLayer();
			symbolplugin.addLayer(barsymbollayer);
			barsymbollayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));
			barsymbollayer.addSymbol(barsymbolgroup);
			barsymbollayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));
			for (int i = 0; i < abarsymbol.length; i++) {
				abarsymbol[i].setAscentValue(0.0D);
				barsymbolgroup.addSymbol(abarsymbol[i]);
				barsymbolgroup.addSymbol((BarSymbol) SymbolComponent.createStrut(
						BarSymbol.class, 5D));
			}
			view.repaintView();
			for (int j = 0; j < abarsymbol.length; j++) {
				abarsymbol[j].inflate(value[j], 0, 1, 1);
			}
			view.repaintDevice();
			for (int k = 0; k < abarsymbol.length; k++) {
				BarDefaultAxisLabel bardefaultaxislabel = new BarDefaultAxisLabel("",
						Color.WHITE);
				bardefaultaxislabel.setFont(InputFonts.getNeuropol(12));
				bardefaultaxislabel.setTextColor(ColorPalette.GRAY);
				bardefaultaxislabel.setText(abarsymbol[k].getSymbol());
				bardefaultaxislabel.setTextPaddingY(1);
				bardefaultaxislabel.setOffsetY(10);
//				bardefaultaxislabel.setOffsetX(10);
//				bardefaultaxislabel.setTextPaddingX(10);
//				bardefaultaxislabel.setTextPaddingY(10);
				abarsymbol[k].setAxisLabel(bardefaultaxislabel);
				view.repaintPart(WindowPart.South);
			}

			//			Legend legend2 = new Legend("Node");
			//			legend2.setLegendFill(new LegendFill1(Color.WHITE, color2));
			//			legend2.setFont(InputFonts.getFont(InputFonts.NO_MOVE, 14));
			//			legend2
			//			.setConstraints(new LegendConstraints(
			//					com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendPosition.East,
			//					0.5F,
			//					com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendAlignment.Middle));
			//			legendplugin.addLegend(legend2);
			//			view.repaintView();
			//			com.jensoft.sw2d.core.window.Window2D.Linear linear1 = new com.jensoft.sw2d.core.window.Window2D.Linear(
			//					0.0D, 0.0D, 0.0D, maxNode);
			//			view.registerWindow2D(linear1);
			//			AxisMilliMetrics axismillimetrics1 = new AxisMilliMetrics(0.0D,
			//					com.jensoft.sw2d.core.plugin.metrics.axis.AxisMetricsPlugin.Axis.AxisEast);
			//			axismillimetrics1.setMajor(300D);
			//			axismillimetrics1.setMedian(200D);
			//			axismillimetrics1.setMinor(100D);
			//			axismillimetrics1.setMetricsLabelColor(color2);
			//			axismillimetrics1.setMetricsMarkerColor(color2);
			//			linear1.registerPlugin(axismillimetrics1);
			//			view.repaintView();
			//			SymbolPlugin symbolplugin1 = new SymbolPlugin();
			//			symbolplugin1
			//			.setNature(com.jensoft.sw2d.core.plugin.symbol.SymbolPlugin.SymbolNature.Vertical);
			//			symbolplugin1.setPriority(100);
			//			linear1.registerPlugin(symbolplugin1);
			//			PointSymbolLayer pointsymbollayer = new PointSymbolLayer();
			//			symbolplugin1.addLayer(pointsymbollayer);
			//			int index_02 = value.length;
			//			PointSymbol apointsymbol[] = new PointSymbol[index_02];
			//			for(int i=0;i<index_02;i++) {
			//				PointSymbol pointsymbol11 = new PointSymbol(node[i]);
			//				apointsymbol[i] = pointsymbol11;
			//			}
			//			pointsymbollayer.addSymbol(SymbolComponent.createGlue(PointSymbol.class));
			//			for (int l = 0; l < apointsymbol.length; l++) {
			//				ImageIcon imageicon = new ImageIcon(System.getProperty("user.dir") + "/img" + File.separator + "insident_r.png");
			//				PointSymbolImage pointsymbolimage = new PointSymbolImage(imageicon.getImage());
			//				apointsymbol[l].setVisible(false);
			//				apointsymbol[l].addPointSymbolPainter(pointsymbolimage);
			//				pointsymbollayer.addSymbol(apointsymbol[l]);
			//				pointsymbollayer.addSymbol(SymbolComponent.createStrut(PointSymbol.class, 45D));
			//			}
			//			pointsymbollayer.addSymbol(SymbolComponent.createGlue(PointSymbol.class));
			//			for (int i1 = 0; i1 < apointsymbol.length; i1++) {
			//				apointsymbol[i1].setVisible(true);
			//				view.repaintDevice();
			//			}



			view.repaintDevice();
			Pie pie = PieToolkit.createPie("pie", 30);
			pie.setStartAngleDegree(10D);
			PieSlice pieslice = PieToolkit.createSlice("s1", ColorPalette.alpha(color2, 240), persentCoinLock, 0);
			PieSlice pieslice1 = PieToolkit.createSlice("s2", color, (100-persentCoinLock), 0);
			PieToolkit.pushSlices(pie, new PieSlice[]{pieslice, pieslice1});
			pie.setPieNature(com.jensoft.sw2d.core.plugin.pie.Pie.PieNature.PieDevice);
			pie.setCenterX(750D);
			pie.setCenterY(80D);
			PiePlugin pieplugin = new PiePlugin();
			pieplugin.setPriority(100);
			pieplugin.addPie(pie);
			linear.registerPlugin(pieplugin);
			view.repaintDevice();
			pieslice.setDivergence(10);
			view.repaintDevice();
			PieLinearEffect pielineareffect = new PieLinearEffect();
			pie.setPieEffect(pielineareffect);
			view.repaintDevice();
			PieReflectionEffect piereflectioneffect = new PieReflectionEffect();
			PieCompoundEffect piecompoundeffect = new PieCompoundEffect(
					new AbstractPieEffect[]{pielineareffect, piereflectioneffect});
			pielineareffect.setOffsetRadius(2);
			pie.setPieEffect(piecompoundeffect);
			view.repaintDevice();
			PieBorderLabel pieborderlabel = new PieBorderLabel();
			pieborderlabel.setLabelFont(InputFonts.getNoMove(10));
			pieborderlabel.setFillColor(ColorPalette.alpha(color2, 240));
			pieborderlabel.setOutlineColor(ColorPalette.alpha(color2, 240));
			pieborderlabel.setLinkColor(ColorPalette.alpha(color2, 240));
			pieborderlabel.setLabelPaddingY(2);
			pieborderlabel.setLabelPaddingX(4);
			pieborderlabel.setOutlineRound(12);
			float af[] = {0.0F, 0.5F, 1.0F};
			Color acolor[] = {Color.DARK_GRAY, Color.BLACK, Color.DARK_GRAY};
			pieborderlabel.setShader(af, acolor);
			pieborderlabel.setLabel("Coins locked");
			pieslice.addSliceLabel(pieborderlabel);
			view.repaintDevice();
			view.repaint();
			String fileName = System.getProperty("user.dir") + "/src/main/resources/static/WEB-INF/img" + File.separator + coin+".png";
			System.out.println(fileName);
			try {
				view.setSize(1000, 650);
				int w = (int) view.getBounds().getWidth();
				int h = (int) view.getBounds().getHeight();
				BufferedImage image = view.getImageView(w, h);
				try {
					FileOutputStream out = new FileOutputStream(fileName);
					ImageIO.write(image, "png".toLowerCase(), out);
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("End");
	}
}
