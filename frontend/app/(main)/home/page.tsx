import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

export default function DashboardHomePage() {
  return (
    <div className="wedding-container py-6 space-y-4">
      <h1 className="text-2xl font-bold">홈 대시보드</h1>
      <div className="grid gap-4 tablet:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>D-Day</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-primary">D-120</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>준비 진행률</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-primary">35%</p>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
