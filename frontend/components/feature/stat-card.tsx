import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

function StatCard({
  title,
  value,
  helper,
}: {
  title: string;
  value: string;
  helper?: string;
}) {
  return (
    <Card>
      <CardHeader>
        <CardTitle className="text-base">{title}</CardTitle>
      </CardHeader>
      <CardContent>
        <p className="text-3xl font-bold text-primary">{value}</p>
        {helper ? <p className="mt-2 text-sm text-text-secondary">{helper}</p> : null}
      </CardContent>
    </Card>
  );
}

export { StatCard };
