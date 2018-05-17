package sample;

public abstract class PointsStrategy {
    public abstract double CalculatePoints(double par1, double par2, double par3);
}

class StockPoints extends PointsStrategy {
    @Override
    public double CalculatePoints(double par1, double par2, double par3) {
        return (par1+par2+par3)/3;
    }
}

class SuperPoints extends PointsStrategy {
    @Override
    public double CalculatePoints(double par1, double par2, double par3) {
        return (par1*1.7+par2*1.5+par3*0.9)/3;
    }
}
